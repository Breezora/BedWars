package net.alphalightning.bedwars.game.state.lobby;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public abstract sealed class PremiumJoin
        implements Listener
        permits QueuedPremiumJoin, RandomizedPremiumJoin, StackedPremiumJoin {

    protected final BedWarsPlugin plugin;

    public PremiumJoin(BedWarsPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public abstract void onLogin(PlayerLoginEvent event);

    public abstract Player findKickablePlayer();

    public boolean isServerFull() {
        return Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers();
    }

}
