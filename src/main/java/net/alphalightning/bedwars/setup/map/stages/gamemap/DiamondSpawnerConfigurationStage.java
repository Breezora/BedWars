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

public class DiamondSpawnerConfigurationStage extends Stage implements LocationConfiguration {

    private final List<Location> locations = new ArrayList<>();
    private final int count;
    private int phase;

    public DiamondSpawnerConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);

        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.count = 0;
            return;
        }
        this.count = gameMapSetup.diamondSpawnerCount();
    }

    @Override
    public void run() {
        if (count == 0) { // No diamond spawner have been configured previously in the setup
            player.sendMessage(Component.translatable("mapsetup.stage.8.error.no-spawner"));
            Feedback.warning(player);

            if (!(setup instanceof GameMapSetup gameMapSetup)) {
                return;
            }
            gameMapSetup.startStage(9);
            return;
        }

        player.sendMessage(Component.translatable("mapsetup.stage.8", Component.text(count)));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > count) {
            return;
        }
        this.phase = phase;

        player.sendMessage(Component.translatable("mapsetup.stage.8.id", Component.text(phase)));
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
        if (isNotStage(8)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        // Emerald spawner configuration is not completed

        if (phase < count) {
            sendSuccessMessage();
            Feedback.success(player);

            locations.add(location);
            startPhase(++phase);
            return;
        }

        // Configuration is completed

        sendSuccessMessage();
        player.sendMessage(Component.translatable("mapsetup.stage.8.success"));
        Feedback.success(player);

        gameMapSetup.configureDiamondSpawnerLocations(locations);
        gameMapSetup.startStage(9);
    }

    private void sendSuccessMessage() {
        player.sendMessage(Component.translatable("mapsetup.stage.8.id.success", Component.text(phase)));
    }
}
