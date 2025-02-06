package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.visual.VisualisationRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class SingleLineRenderer implements VisualisationRenderer<SingleLineVisualisation> {

    private final BedWarsPlugin plugin;
    private final Player player;

    public SingleLineRenderer(BedWarsPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull SingleLineVisualisation visualisation) {
        final Location start = this.player.getEyeLocation();
        return Bukkit.getScheduler().runTaskTimer(plugin, () -> visualisation.show(start), 0L, 5L);
    }
}
