package net.alphalightning.bedwars.game.state.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public final class RandomizedPremiumJoin extends PremiumJoin {

    @Override
    public void onJoin(PlayerJoinEvent event) {
    }

    @Override
    public Player findKickablePlayer() {
        return null;
    }
}
