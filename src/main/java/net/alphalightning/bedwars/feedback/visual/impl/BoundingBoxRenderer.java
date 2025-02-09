package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class BoundingBoxRenderer<T> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final BedWarsPlugin plugin;
    private final MapSetup setup;

    public BoundingBoxRenderer(BedWarsPlugin plugin, MapSetup setup) {
        this.plugin = plugin;
        this.setup = setup;
    }

    public @NotNull BukkitTask render(@NotNull T visualization, int color) {
        return this.visualizationManager.registerTask(this.setup, Bukkit.getScheduler().runTaskTimer(this.plugin, () -> new BoundingBoxVisualization<T>(color).show(visualization), 0L, 5L));
    }

}
