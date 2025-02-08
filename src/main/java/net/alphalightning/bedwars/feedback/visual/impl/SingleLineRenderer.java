package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class SingleLineRenderer implements VisualizationRenderer<SingleLineVisualization> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final BedWarsPlugin plugin;
    private final MapSetup setup;
    private final Player player;

    public SingleLineRenderer(BedWarsPlugin plugin, MapSetup setup, Player player) {
        this.plugin = plugin;
        this.setup = setup;
        this.player = player;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull SingleLineVisualization visualisation) {
        final Location start = this.player.getEyeLocation();
        return this.visualizationManager.registerTask(this.setup, Bukkit.getScheduler().runTaskTimer(plugin, () -> visualisation.show(start), 0L, 5L));
    }
}
