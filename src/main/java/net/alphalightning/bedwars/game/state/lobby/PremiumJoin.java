package net.alphalightning.bedwars.game.state.lobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract sealed class PremiumJoin
        implements Listener
        permits QueuedPremiumJoin, RandomizedPremiumJoin, StackedPremiumJoin {

    protected static final String PREMIUM_PERMISSION = "bedwars.join.premium";

    public abstract void onJoin(PlayerJoinEvent event);

    public abstract void onQuit(PlayerQuitEvent event);

    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (!isServerFull()) {
            event.allow();
            return;
        }
        if (!player.hasPermission(PREMIUM_PERMISSION)) return;

        Player kickable = findKickablePlayer();
        if (kickable == null) return;

        kickable.kick(GlobalTranslator.render(Component.translatable("state.lobby.kick"), player.locale()));
        event.allow();
    }

    public abstract Player findKickablePlayer();

    private boolean isServerFull() {
        return Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers();
    }

}
