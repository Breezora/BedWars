package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.feedback.visual.renderer.BoundingBoxRenderer;
import net.alphalightning.bedwars.feedback.visual.renderer.LootspawnerRenderer;
import net.alphalightning.bedwars.feedback.visual.renderer.LootspawnerVisualization;
import net.alphalightning.bedwars.game.SpawnerType;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;
import java.util.List;

public class EmeraldSpawnerConfigurationStage extends Stage implements LocationConfiguration {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final List<Location> locations = new ArrayList<>();
    private final int count;
    private int phase;

    public EmeraldSpawnerConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);

        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.count = 0;
            return;
        }
        this.count = gameMapSetup.emeraldSpawnerCount();
    }

    @Override
    public void run() {
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }
        if (count == 0) { // No emerald spawner have been configured previously in the setup
            player.sendMessage(Component.translatable("mapsetup.stage.7.error.no-spawner"));
            Feedback.warning(player);

            gameMapSetup.startStage(8);
            return;
        }

        player.sendMessage(Component.translatable("mapsetup.stage.7", Component.text(count)));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > count) {
            return;
        }
        this.phase = phase;

        player.sendMessage(Component.translatable("mapsetup.stage.7.id", Component.text(phase)));
        Feedback.success(player);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Location location = player.getLocation();

        if (isNotPlayerConfiguring(event.getPlayer())) {
            return;
        }
        if (isNotOnGround(player, location)) {
            return;
        }
        if (isNotStage(7)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        // Emerald spawner configuration is not completed

        final Location withOffset = location.add(OFFSET);
        locations.add(withOffset);

        this.visualizationManager.registerTask(gameMapSetup, new LootspawnerRenderer(plugin, gameMapSetup, withOffset)
                .render(new LootspawnerVisualization(plugin, setup, SpawnerType.EMERALD, false))
        );
        this.visualizationManager.registerTask(gameMapSetup, new BoundingBoxRenderer<Block>(plugin, gameMapSetup)
                .render(withOffset.getBlock(), 0x21de3a)
        );

        player.sendMessage(Component.translatable("mapsetup.stage.7.id.success", Component.text(phase)));
        Feedback.success(player);

        if (phase < count) {
            startPhase(++phase);
            return;
        }

        // Configuration is completed

        player.sendMessage(Component.translatable("mapsetup.stage.7.success"));
        gameMapSetup.configureEmeraldSpawnerLocations(locations);
        gameMapSetup.startStage(GameMapSetup.DIAMOND_SPAWNER_CONFIGURATION_STAGE);
    }
}
