package net.alphalightning.bedwars.game.state.lobby;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

public final class QueuedPremiumJoin extends PremiumJoin {

    public QueuedPremiumJoin(BedWarsPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onLogin(PlayerLoginEvent event) {
    }

    @Override
    public Player findKickablePlayer() {
        return null;
    }
}
