package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class BoundingBoxRenderer implements VisualizationRenderer<BoundingBoxVisualization> {

    private final BedWarsPlugin plugin;
    private final Location location;

    public BoundingBoxRenderer(BedWarsPlugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull BoundingBoxVisualization visualisation) {
        return Bukkit.getScheduler().runTaskTimer(plugin, () -> visualisation.show(this.location), 0L, 5L);
    }
}
