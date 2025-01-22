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
        if (count == 0) { // No emerald spawner have been configured previously in the setup
            player.sendMessage(Component.translatable("mapsetup.stage.7.error.no-spawner"));
            Feedback.warning(player);

            if (!(setup instanceof GameMapSetup gameMapSetup)) {
                return;
            }
            gameMapSetup.startStage(8);
            return;
        }

        player.sendMessage(Component.translatable("mapsetup.stage.7", Component.text(count)));
        startStage(1);
    }

    private void startStage(int stage) {
        if (stage > count) {
            return;
        }
        this.stage = stage;

        player.sendMessage(Component.translatable("mapsetup.stage.7.id", Component.text(stage)));
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
            player.sendMessage(Component.translatable("mapsetup.stage.7.id.success", Component.text(stage)));
            Feedback.success(player);

            locations.add(location);
            startStage(++stage);
            return;
        }

        // Configuration is completed

        player.sendMessage(Component.translatable("mapsetup.stage.7.success"));
        Feedback.success(player);

        gameMapSetup.configureEmeraldSpawnerLocations(locations);
        gameMapSetup.startStage(8);
    }
}
