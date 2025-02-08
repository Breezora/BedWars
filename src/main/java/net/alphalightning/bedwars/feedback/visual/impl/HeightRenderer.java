package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class HeightRenderer implements VisualizationRenderer<HeightVisualization> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final BedWarsPlugin plugin;
    private final MapSetup setup;
    private final Player player;

    public HeightRenderer(BedWarsPlugin plugin, MapSetup setup, Player player) {
        this.plugin = plugin;
        this.setup = setup;
        this.player = player;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull HeightVisualization visualisation) {
        return this.visualizationManager.registerTask(this.setup, Bukkit.getScheduler().runTaskTimer(plugin, () -> visualisation.show(player.getLocation()), 0L, 5L));
    }
}
