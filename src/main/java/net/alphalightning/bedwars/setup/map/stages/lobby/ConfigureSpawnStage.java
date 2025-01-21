package net.alphalightning.bedwars.setup.map.stages.lobby;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.LobbyMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ConfigureSpawnStage extends Stage {

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

        if (this.player == null || !this.player.equals(player)) {
            return;
        }
        if (!player.isSneaking() || location.subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
            return;
        }
        if (setup.stage() != 1) {
            return;
        }
        if (!(setup instanceof LobbyMapSetup lobbyMapSetup)) {
            return;
        }

        lobbyMapSetup.spawn(location);
        setup.startStage(2);
    }
}
