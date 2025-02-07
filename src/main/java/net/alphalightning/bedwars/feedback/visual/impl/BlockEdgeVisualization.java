package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.feedback.visual.BlockVisualization;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class BlockEdgeVisualization implements BlockVisualization<Block> {

    private final int color;

    public BlockEdgeVisualization(int color) {
        this.color = color;
    }

    @Override
    public void show(@NotNull Block block) {
        final BoundingBox boundingBox = BoundingBox.of(block);
        visualizeBoundingBox(block.getWorld(), boundingBox,  this.color);
    }
}
