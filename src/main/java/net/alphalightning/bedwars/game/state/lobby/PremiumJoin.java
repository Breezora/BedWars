package net.alphalightning.bedwars.game.state.lobby;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public abstract sealed class PremiumJoin
        implements Listener
        permits QueuedPremiumJoin, RandomizedPremiumJoin, StackedPremiumJoin {

    protected static final String PREMIUM_PERMISSION = "bedwars.join.premium";

    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (!isServerFull()) return;
        if (!player.hasPermission(PREMIUM_PERMISSION)) return;

        Player kickable = findKickablePlayer();
        if (kickable == null) return;

        kickable.kick(Component.translatable("lobby.kick.premium"));
        event.allow();
    }

    public abstract Player findKickablePlayer();

    public boolean isPlayerKickable(Player player) {
        return player != null && player.hasPermission(PREMIUM_PERMISSION);
    }

    private boolean isServerFull() {
        return Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers();
    }

}
