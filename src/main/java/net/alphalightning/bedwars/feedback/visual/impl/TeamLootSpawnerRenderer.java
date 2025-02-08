package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class TeamLootSpawnerRenderer implements VisualizationRenderer<TeamLootspawnerVisualization> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final BedWarsPlugin plugin;
    private final MapSetup setup;
    private final Location location;

    public TeamLootSpawnerRenderer(BedWarsPlugin plugin, MapSetup setup, Location location) {
        this.plugin = plugin;
        this.setup = setup;
        this.location = location;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull TeamLootspawnerVisualization visualisation) {
        return this.visualizationManager.registerTask(this.setup, Bukkit.getScheduler().runTaskTimer(this.plugin, () -> visualisation.show(this.location), 0L, 10L));
    }
}
