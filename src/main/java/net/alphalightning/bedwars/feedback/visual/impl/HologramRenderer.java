package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class HologramRenderer implements VisualizationRenderer<HologramVisualization> {

    private final BedWarsPlugin plugin;
    private final Location location;

    public HologramRenderer(BedWarsPlugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull HologramVisualization visualisation) {
        return Bukkit.getScheduler().runTask(this.plugin, () -> visualisation.show(this.location));
    }
}
