package net.alphalightning.bedwars.setup.map.stages.lobby;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.LobbyMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ConfigureHologramStage extends Stage implements LocationConfiguration {

    public ConfigureHologramStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        Feedback.success(player);
        player.sendMessage(Component.translatable("lobbysetup.stage.2"));
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
        if (setup.stage() != 2) {
            return;
        }
        if (!(setup instanceof LobbyMapSetup lobbyMapSetup)) {
            return;
        }

        lobbyMapSetup.hologram(location);
        setup.finish(3);
    }
}
