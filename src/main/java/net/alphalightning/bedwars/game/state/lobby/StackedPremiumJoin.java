package net.alphalightning.bedwars.game.state.lobby;

import org.bukkit.entity.Player;

public final class StackedPremiumJoin extends PremiumJoin {

    @Override
    public Player findKickablePlayer() {
        return null;
    }
}
