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
        queue.add(event.getPlayer());
    }

    @Override
    public void onQuit(PlayerQuitEvent event) {
        queue.remove(event.getPlayer());
    }

    @Override
    public Player findKickablePlayer() {
        int queueSize = queue.size();

        for (int i = 0; i < queueSize; i++) {
            Player kickable = queue.poll();

            if (isPlayerKickable(kickable)) {
                return kickable;
            }
            if (kickable != null) {
                queue.add(kickable);
            }
        }
        return null;
    }
}
