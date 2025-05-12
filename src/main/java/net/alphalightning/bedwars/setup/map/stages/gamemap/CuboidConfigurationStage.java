package net.alphalightning.bedwars.setup.map.stages.gamemap;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.feedback.visual.renderer.BoundingBoxRenderer;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
import net.alphalightning.bedwars.setup.map.stages.ApprovableConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.map.stages.TeamConfiguration;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.alphalightning.bedwars.util.CuboidSelection;
import net.alphalightning.bedwars.util.SelectionWandTool;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuboidConfigurationStage extends Stage implements TeamConfiguration, ApprovableConfiguration {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final List<JacksonTeam> teams;
    private final int count;
    private int phase;
    private boolean undoUsed = false;

    private final List<CuboidSelection> selections = new ArrayList<>();
    private final SelectionWandTool tool;

    private TranslatableComponent teamName = null;
    private JacksonTeam team = null;

    public CuboidConfigurationStage(@NotNull BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.teams = Collections.emptyList();
            this.tool = null;
            this.count = 0;
            return;
        }
        this.tool = new SelectionWandTool(player);
        this.teams = gameMapSetup.teams();
        this.count = gameMapSetup.teams().size();
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.16", NamedTranslationArgument.component("tool", Component.translatable("item.selection_wand"))));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > count) return;

        this.phase = phase;
        this.undoUsed = false;
        this.team = teams.get(phase - 1);
        this.teamName = Component.translatable("team." + convertName(team.name()));

        tool.reset();

        player.sendMessage(Component.translatable("mapsetup.stage.16.name",
                NamedTranslationArgument.numeric("phase", phase),
                NamedTranslationArgument.component("team", teamName)
        ));
        Feedback.success(player);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (isNotPlayerConfiguring(event.getPlayer())) return;
        if (isNotStage(GameMapSetup.CUBOID_SELECTION_CONFIGURATION_STAGE)) return;
        if (!(setup instanceof GameMapSetup gameMapSetup)) return;
        if (event.getHand() != EquipmentSlot.HAND) return;

        if (selections.size() >= phase) return; // Warte auf Bestätigung/Reset der aktuell getroffenen Auswahl

        tool.onToolUse(event);

        if (!tool.isComplete()) return;
        if (!tool.first().getWorld().equals(tool.second().getWorld())) {
            player.sendMessage(Component.translatable("mapsetup.stage.16.world"));
            Feedback.error(player);
            return;
        }

        CuboidSelection selection = new CuboidSelection(tool.first(), tool.second());

        undoUsed = false;
        selections.add(selection);

        new BoundingBoxRenderer<List<Block>>(plugin, gameMapSetup).render(selection.corners(), team.color());
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (isNotPlayerConfiguring(event.getPlayer())) return;
        if (isNotStage(GameMapSetup.CUBOID_SELECTION_CONFIGURATION_STAGE)) return;
        if (!(setup instanceof GameMapSetup gameMapSetup)) return;

        event.setCancelled(true);

        String message = event.signedMessage().message();

        if (!VALID_MESSAGES.contains(message.toLowerCase())) {
            player.sendMessage(Component.translatable("mapsetup.stage.approval.tip"));
            Feedback.error(player);
            return;
        }

        boolean isApproved = isApproved(message);

        if (selections.size() < phase) {
            player.sendMessage(Component.translatable("mapsetup.state.error.missing-selection"));
            Feedback.error(player);
            return;
        }

        if (!isApproved) {
            if (undoUsed) {
                player.sendMessage(Component.translatable("mapsetup.stage.error.undo"));
                Feedback.error(player);
                return;
            }

            player.sendMessage(Component.translatable("mapsetup.stage.16.undo", NamedTranslationArgument.component("team", teamName)));
            visualizationManager.removeLastTask(setup);
            selections.removeLast();
            undoUsed = true;
            tool.reset();
            return;
        }

        player.sendMessage(Component.translatable("mapsetup.stage.16.name.success", NamedTranslationArgument.component("team", teamName)));
        Feedback.success(player);

        if (phase < count) {
            startPhase(++phase);
            return;
        }

        gameMapSetup.configureSelections(selections);
        gameMapSetup.startStage(GameMapSetup.FLOOD_FILL_CONFIGURATION_STAGE);
    }

    private boolean isApproved(String message) {
        return message.equalsIgnoreCase(YES) || message.equalsIgnoreCase(YES_ALIAS);
    }
}
