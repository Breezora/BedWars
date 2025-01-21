package net.alphalightning.bedwars.setup.map.stages;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class CancelStage extends Stage {

    private static final String CANCEL_MESSAGE = "cancel";

    private final boolean isLobbySetup;

    public CancelStage(BedWarsPlugin plugin, Player player, MapSetup setup, boolean isLobbySetup) {
        super(plugin, player, setup);
        this.isLobbySetup = isLobbySetup;
    }

    @Override
    public void run() {
        Component component = Component.translatable(isLobbySetup ?
                "lobbysetup.cancel" :
                "mapsetup.cancel");

        Feedback.error(player);
        player.sendMessage(component);
        plugin.getComponentLogger().warn(component);
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (player == null || !player.equals(event.getPlayer())) {
            return;
        }

        if (event.signedMessage().message().equalsIgnoreCase(CANCEL_MESSAGE)) {
            event.setCancelled(true);
            setup.cancel();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (player != null && player.equals(event.getPlayer())) {
            setup.cancel();
        }
    }

}
