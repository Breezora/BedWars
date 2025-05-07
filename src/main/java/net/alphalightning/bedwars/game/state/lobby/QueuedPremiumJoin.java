package net.alphalightning.bedwars.game.state.lobby;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.entity.Player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class QueuedPremiumJoin extends PremiumJoin {

    private final Queue<Player> queue = new ConcurrentLinkedQueue<>();

    public QueuedPremiumJoin(BedWarsPlugin plugin) {
        super(plugin);
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
