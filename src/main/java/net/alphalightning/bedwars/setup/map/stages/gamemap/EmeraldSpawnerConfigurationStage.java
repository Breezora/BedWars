package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;
import java.util.List;

public class EmeraldSpawnerConfigurationStage extends Stage implements LocationConfiguration {

    private final List<Location> locations = new ArrayList<>();

    private final int count;
    private int stage;
    private int readableStage;

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
        player.sendMessage(Component.translatable("mapsetup.stage.7"));
        startStage(0);
    }

    private void startStage(int stage) {
        if (count == 0) {
            player.sendMessage(Component.translatable("mapsetup.stage.7.error.no-spawner"));
            Feedback.warning(player);
            return;
        }
        if (stage > count) {
            return;
        }
        this.stage = stage;
        this.readableStage = stage + 1;

        player.sendMessage(Component.translatable("mapsetup.stage.7.id", Component.text(readableStage)));
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

        if (stage < count) {
            player.sendMessage(Component.translatable("mapsetup.stage.7.id.success", Component.text(readableStage)));
            Feedback.success(player);

            locations.add(location);
            startStage(stage + 1);
            return;
        }

        // Configuration is completed

        player.sendMessage(Component.translatable("mapsetup.stage.7.success"));
        Feedback.success(player);

        gameMapSetup.configureEmeraldSpawnerLocations(locations);
        gameMapSetup.startStage(8);
    }
}
