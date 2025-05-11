package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.feedback.visual.renderer.BoundingBoxRenderer;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuboidConfigurationStage extends Stage implements TeamConfiguration {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final List<JacksonTeam> teams;
    private final int count;
    private int phase;

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

        tool.onToolUse(event);

        if (!tool.isComplete()) return;

        CuboidSelection selection = new CuboidSelection(team, tool.first(), tool.second());
        selections.add(selection);
        visualizationManager.registerTask(gameMapSetup, new BoundingBoxRenderer<List<Block>>(plugin, gameMapSetup).render(selection.corners(), team.color()));

        if (phase < count) {
            startPhase(++phase);
            return;
        }

        player.sendMessage(Component.translatable("mapsetup.stage.16.name.success", NamedTranslationArgument.component("team", teamName)));
        gameMapSetup.configureSelections(selections);
        gameMapSetup.startStage(GameMapSetup.FLOOD_FILL_CONFIGURATION_STAGE);
    }

}
