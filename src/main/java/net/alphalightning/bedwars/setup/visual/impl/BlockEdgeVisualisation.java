package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.setup.visual.BlockVisualisation;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class BlockEdgeVisualisation implements BlockVisualisation<Block> {

    private final int color;

    public BlockEdgeVisualisation(int color) {
        this.color = color;
    }

    @Override
    public void show(@NotNull Block block) {
        final BoundingBox boundingBox = BoundingBox.of(block);
        visualizeBoundingBox(block.getWorld(), boundingBox,  this.color);
    }
}
