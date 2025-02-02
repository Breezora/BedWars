package net.alphalightning.bedwars.setup.map.stages;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.exception.NotRegisteredException;
import net.alphalightning.bedwars.manager.PlayerManager;
import net.alphalightning.bedwars.setup.ConfigurationType;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public abstract class Stage implements Listener {

    protected final PlayerManager<ConfigurationType> playerManager;
    protected final BedWarsPlugin plugin;
    protected final MapSetup setup;
    protected Player player;

    public Stage(@NotNull BedWarsPlugin plugin, Player player, MapSetup setup) {
        this.plugin = plugin;
        this.player = player;
        this.setup = setup;
        this.playerManager = plugin.setupPlayerManager();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public boolean isNotPlayerConfiguring(Player player) {
        return this.player == null || !this.player.equals(player);
    }

    public boolean isNotStage(int stage) {
        return setup.stage() != stage;
    }

    public void invalidate() {
        try {
            playerManager.unregisterPlayer(player);

        } catch (NotRegisteredException exception) {
            player.sendMessage(Component.translatable("error.setup.none"));

        } finally {
            player = null;
        }
    }

    public abstract void run();
}
