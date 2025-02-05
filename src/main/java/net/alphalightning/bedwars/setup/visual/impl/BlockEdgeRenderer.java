package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.visual.VisualizationRenderer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;


public class BlockEdgeRenderer implements VisualizationRenderer<BlockEdgeVisualisation> {

    private final BedWarsPlugin plugin;
    private final Block block;

    public BlockEdgeRenderer(BedWarsPlugin plugin, Block block) {
        this.plugin = plugin;
        this.block = block;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull BlockEdgeVisualisation visualisation) {
        return Bukkit.getScheduler().runTaskTimer(plugin, () -> visualisation.show(block), 0L, 5L);
    }
}
