package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.visual.VisualizationRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class FakeBlockRenderer implements VisualizationRenderer<FakeBlockVisualization> {

    private final BedWarsPlugin plugin;
    private final Location location;

    public FakeBlockRenderer(BedWarsPlugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull FakeBlockVisualization visualisation) {
        return Bukkit.getScheduler().runTask(plugin, () -> visualisation.show(location));
    }
}
