package net.alphalightning.bedwars.game.state.lobby;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

public final class StackedPremiumJoin extends PremiumJoin {

    public StackedPremiumJoin(BedWarsPlugin plugin) {
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
