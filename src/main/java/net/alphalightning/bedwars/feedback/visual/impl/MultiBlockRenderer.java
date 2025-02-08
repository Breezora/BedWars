package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultiBlockRenderer implements VisualizationRenderer<MultiBlockVisualization> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final BedWarsPlugin plugin;
    private final MapSetup setup;
    private final List<Block> blocks;

    public MultiBlockRenderer(BedWarsPlugin plugin, MapSetup setup, List<Block> blocks) {
        this.plugin = plugin;
        this.setup = setup;
        this.blocks = blocks;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull MultiBlockVisualization visualisation) {
        return this.visualizationManager.registerTask(this.setup, Bukkit.getScheduler().runTaskTimer(this.plugin, () -> visualisation.show(this.blocks), 0L, 5L));
    }
}
