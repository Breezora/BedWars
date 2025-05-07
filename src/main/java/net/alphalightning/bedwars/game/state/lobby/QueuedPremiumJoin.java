package net.alphalightning.bedwars.game.state.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class QueuedPremiumJoin extends PremiumJoin {

    private final Queue<Player> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission(PREMIUM_PERMISSION)) {
            queue.add(event.getPlayer());
        }
    }

    @Override
    public void onQuit(PlayerQuitEvent event) {
        queue.remove(event.getPlayer());
    }

    @Override
    public Player findKickablePlayer() {
        return queue.isEmpty() ? null : queue.peek();
    }
}
