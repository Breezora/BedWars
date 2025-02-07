package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.feedback.visual.BlockVisualization;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultiBlockVisualization implements BlockVisualization<List<Block>> {

    private final int color;

    public MultiBlockVisualization(int color) {
        this.color = color;
    }

    @Override
    public void show(@NotNull List<Block> blocks) {
        if (blocks.size() != 2) {
            return;
        }

        final BoundingBox boundingBox = BoundingBox.of(blocks.getFirst(), blocks.getLast());
        visualizeBoundingBox(blocks.getFirst().getWorld(), boundingBox, this.color);
    }
}
