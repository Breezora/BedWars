package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.feedback.visual.BlockVisualization;
import net.alphalightning.bedwars.util.BlockUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BoundingBoxVisualization<T> implements BlockVisualization<T> {

    private final int color;

    public BoundingBoxVisualization(int color) {
        this.color = color;
    }

    @Override
    public void show(@NotNull T t) {
        if (t instanceof Location location) {
            BoundingBox boundingBox = BlockUtil.fromLocation(location, 1);
            visualizeBoundingBox(location.getWorld(), boundingBox, this.color);

        } else if (t instanceof Block block) {
            BoundingBox boundingBox = BoundingBox.of(block);
            visualizeBoundingBox(block.getWorld(), boundingBox, this.color);

        }
        if (t instanceof List<?> blocks) {
            if (blocks.size() != 2) {
                throw new IllegalStateException();
            }

            Object firstObject = blocks.getFirst();
            Object lastObject = blocks.getLast();

            if (!(firstObject instanceof Block first) || !(lastObject instanceof Block last)) {
                throw new IllegalArgumentException();
            }

            BoundingBox boundingBox = BoundingBox.of(first, last);
            visualizeBoundingBox(first.getWorld(), boundingBox, this.color);

        } else {
            throw new UnsupportedOperationException();
        }
    }
}
