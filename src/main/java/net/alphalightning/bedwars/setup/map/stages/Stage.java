package net.alphalightning.bedwars.setup.map.stages;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Stage implements Listener {

    protected final BedWarsPlugin plugin;
    protected final MapSetup setup;
    protected final Player player;

    public Stage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        this.plugin = plugin;
        this.player = player;
        this.setup = setup;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public abstract void run();
}
