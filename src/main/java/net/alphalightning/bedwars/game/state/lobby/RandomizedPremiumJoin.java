package net.alphalightning.bedwars.game.state.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class RandomizedPremiumJoin extends PremiumJoin {

    @Override
    public void onJoin(PlayerJoinEvent event) {
    }

    @Override
    public void onQuit(PlayerQuitEvent event) {

    }

    @Override
    public Player findKickablePlayer() {
        return null;
    }
}
