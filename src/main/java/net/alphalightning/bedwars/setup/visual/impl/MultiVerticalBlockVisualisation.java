package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.setup.visual.BlockVisualisation;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class MultiVerticalBlockVisualisation implements BlockVisualisation<Block[]> {

    private final int color;

    public MultiVerticalBlockVisualisation(int color) {
        this.color = color;
    }

    @Override
    public void show(Block @NotNull [] blocks) {
        if (blocks.length != 2) {
            return;
        }

        final BoundingBox boundingBox = BoundingBox.of(blocks[0], blocks[1]);
        visualizeBoundingBox(blocks[0].getWorld(), boundingBox, this.color);
    }
}
