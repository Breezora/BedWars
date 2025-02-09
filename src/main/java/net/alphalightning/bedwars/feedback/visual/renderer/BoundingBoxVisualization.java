package net.alphalightning.bedwars.feedback.visual.renderer;

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
        switch (t) {
            case Location location -> {
                final BoundingBox boundingBox = BlockUtil.fromLocation(location, 1);
                visualizeBoundingBox(location.getWorld(), boundingBox, this.color);

            }
            case Block block -> {
                final BoundingBox boundingBox = BoundingBox.of(block);
                visualizeBoundingBox(block.getWorld(), boundingBox, this.color);

            }
            case List<?> list -> {
                if (list.size() != 2) {
                    throw new IllegalStateException();
                }

                final Object firstObject = list.getFirst();
                final Object lastObject = list.getLast();

                if (!(firstObject instanceof Block first) || !(lastObject instanceof Block last)) {
                    throw new IllegalArgumentException();
                }

                final BoundingBox boundingBox = BoundingBox.of(first, last);
                visualizeBoundingBox(first.getWorld(), boundingBox, this.color);

            }
            default -> throw new UnsupportedOperationException();
        }
    }
}
