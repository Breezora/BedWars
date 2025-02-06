package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.setup.visual.BlockVisualisation;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultiVerticalBlockVisualisation implements BlockVisualisation<List<Block>> {

    private final int color;

    public MultiVerticalBlockVisualisation(int color) {
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
