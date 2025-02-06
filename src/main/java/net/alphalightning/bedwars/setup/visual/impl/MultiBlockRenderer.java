package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.visual.VisualisationRenderer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultiBlockRenderer implements VisualisationRenderer<MultiBlockVisualisation> {

    private final BedWarsPlugin plugin;
    private final List<Block> blocks;

    public MultiBlockRenderer(BedWarsPlugin plugin, List<Block> blocks) {
        this.plugin = plugin;
        this.blocks = blocks;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull MultiBlockVisualisation visualisation) {
        return Bukkit.getScheduler().runTaskTimer(this.plugin, () -> visualisation.show(this.blocks), 0L, 5L);
    }
}
