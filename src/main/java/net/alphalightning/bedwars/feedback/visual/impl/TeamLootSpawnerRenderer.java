package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class TeamLootSpawnerRenderer implements VisualizationRenderer<TeamLootspawnerVisualization> {

    private final BedWarsPlugin plugin;
    private final Location location;

    public TeamLootSpawnerRenderer(BedWarsPlugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull TeamLootspawnerVisualization visualisation) {
        return Bukkit.getScheduler().runTaskTimer(this.plugin, () -> visualisation.show(this.location), 0L, 10L);
    }
}
