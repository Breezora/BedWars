package net.alphalightning.bedwars.setup.map.stages.lobby;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.LobbyMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ConfigureSpawnStage extends Stage implements LocationConfiguration {

    public ConfigureSpawnStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("lobbysetup.stage.1"));
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        if (isPlayerNotConfiguring(player)) {
            return;
        }
        if (!isOnGround(player, location)) {
            return;
        }
        if (isNotStage(1)) {
            return;
        }
        if (!(setup instanceof LobbyMapSetup lobbyMapSetup)) {
            return;
        }

        lobbyMapSetup.spawn(location);
        lobbyMapSetup.startStage(2);
    }
}
