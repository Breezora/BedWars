package net.alphalightning.bedwars.game.state.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Stack;

public final class StackedPremiumJoin extends PremiumJoin {

    private final Stack<Player> stack = new Stack<>();

    @Override
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission(PREMIUM_PERMISSION)) {
            stack.push(player);
        }
    }

    @Override
    public void onQuit(PlayerQuitEvent event) {
        stack.remove(event.getPlayer());
    }

    @Override
    public Player findKickablePlayer() {
        return stack.isEmpty() ? null : stack.peek();
    }
}
