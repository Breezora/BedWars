package net.alphalightning.bedwars.setup.map.stages.gamemap;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.feedback.visual.renderer.BoundingBoxRenderer;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.ApprovableConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.alphalightning.bedwars.util.CuboidSelection;
import net.alphalightning.bedwars.util.RegionInformation;
import net.alphalightning.bedwars.util.RegionUtil;
import net.alphalightning.bedwars.util.SelectionWandTool;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnerProtectionConfigurationStage extends Stage implements ApprovableConfiguration {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final List<RegionInformation> informationList = new ArrayList<>();
    private final SelectionWandTool tool;
    private final int count;
    private int phase;

    public SpawnerProtectionConfigurationStage(@NotNull BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.tool = null;
            this.count = 0;
            return;
        }
        this.tool = new SelectionWandTool(player);
        this.count = gameMapSetup.spawner().size();
    }

    @Override
    public void run() {
        if (count == 0) {
            Feedback.warning(player);
            player.sendMessage(Component.translatable("mapsetup.stage.18.skip"));
            setupManager.finishSetup(player, GameMapSetup.COMPLETION_STAGE);
            return;
        }

        player.sendMessage(Component.translatable("mapsetup.stage.18", NamedTranslationArgument.component("tool", Component.translatable("item.selection_wand"))));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > count) return;

        this.phase = phase;
        tool.reset();

        Feedback.success(player);
        player.sendMessage(Component.translatable("mapsetup.stage.18.name", NamedTranslationArgument.numeric("phase", phase)));
        player.sendMessage(Component.translatable("mapsetup.stage.18.tip"));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (isNotPlayerConfiguring(event.getPlayer())) return;
        if (isNotStage(GameMapSetup.SPAWNER_PROTECTION_CONFIGURATION_STAGE)) return;
        if (!(setup instanceof GameMapSetup gameMapSetup)) return;

        tool.onToolUse(event);

        if (!tool.isComplete()) return;
        if (!tool.first().getWorld().equals(tool.second().getWorld())) {
            player.sendMessage(Component.translatable("mapsetup.stage.18.error.world"));
            Feedback.error(player);
            return;
        }

        CuboidSelection selection = new CuboidSelection(tool.first(), tool.second());
        RegionInformation information = RegionUtil.createRegionInformation(selection);

        informationList.add(information);
        visualizationManager.registerTask(gameMapSetup, new BoundingBoxRenderer<List<Block>>(plugin, gameMapSetup).render(selection.corners(), Color.fromRGB(0xF06562).asRGB()));
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (isNotPlayerConfiguring(event.getPlayer())) return;
        if (isNotStage(GameMapSetup.SPAWNER_PROTECTION_CONFIGURATION_STAGE)) return;
        if (!(setup instanceof GameMapSetup gameMapSetup)) return;

        event.setCancelled(true);

        String message = event.signedMessage().message();

        if (!VALID_MESSAGES.contains(message.toLowerCase())) {
            player.sendMessage(Component.translatable("mapsetup.stage.18.tip"));
            Feedback.error(player);
            return;
        }

        boolean isApproved = isApproved(message);

        if (!isApproved) {
            player.sendMessage(Component.translatable("mapsetup.stage.18.undo", NamedTranslationArgument.numeric("phase", phase)));
            cancelVisualization(gameMapSetup);
            tool.reset();
            return;
        }

        player.sendMessage(Component.translatable("mapsetup.stage.18.approve", NamedTranslationArgument.numeric("phase", phase)));
        Feedback.success(player);

        if (phase < count) {
            startPhase(++phase);
            return;
        }

        gameMapSetup.configureRegionInformation(informationList);
        setupManager.finishSetup(player, GameMapSetup.COMPLETION_STAGE);
    }

    private boolean isApproved(String message) {
        return message.equalsIgnoreCase(YES) || message.equalsIgnoreCase(YES_ALIAS);
    }

    private void cancelVisualization(GameMapSetup gameMapSetup) {
        BukkitTask lastTask = visualizationManager.findLastTask(gameMapSetup);
        visualizationManager.cancelVisualization(gameMapSetup, lastTask);
    }
}
