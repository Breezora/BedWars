package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.feedback.visual.BlockVisualization;
import net.alphalightning.bedwars.utils.BlockUtil;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class BoundingBoxVisualization implements BlockVisualization<Location> {

    private final int color;

    public BoundingBoxVisualization(int color) {
        this.color = color;
    }

    @Override
    public void show(@NotNull Location location) {
        final BoundingBox boundingBox = BlockUtil.fromLocation(location, 1);
        visualizeBoundingBox(location.getWorld(), boundingBox, this.color);
    }
}
