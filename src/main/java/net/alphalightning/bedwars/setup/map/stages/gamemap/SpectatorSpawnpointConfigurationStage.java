package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class SpectatorSpawnpointConfigurationStage extends Stage implements LocationConfiguration {

    public SpectatorSpawnpointConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.6"));
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        if (this.player == null || !this.player.equals(player)) {
            return;
        }
        if (!isOnGround(player, location)) {
            return;
        }
        if (setup.stage() != 6) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        gameMapSetup.configureSpectatorSpawn(location);
        gameMapSetup.startStage(7);
    }
}
