package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;


public class BlockEdgeRenderer implements VisualizationRenderer<BlockEdgeVisualization> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final BedWarsPlugin plugin;
    private final MapSetup setup;
    private final Block block;

    public BlockEdgeRenderer(BedWarsPlugin plugin, MapSetup setup, Block block) {
        this.plugin = plugin;
        this.setup = setup;
        this.block = block;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull BlockEdgeVisualization visualisation) {
        return this.visualizationManager.registerTask(this.setup, Bukkit.getScheduler().runTaskTimer(plugin, () -> visualisation.show(block), 0L, 5L));
    }
}
