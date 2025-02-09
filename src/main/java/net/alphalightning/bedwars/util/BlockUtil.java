package net.alphalightning.bedwars.util;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public final class BlockUtil {

    public static @NotNull BlockFace cardinalDirection(@NotNull Player player) {
        float yaw = player.getYaw();
        yaw = (yaw % 360 + 360) % 360; // Normalize into range of [0;360]
        double radians = Math.toRadians(yaw);

        double sin = Math.sin(radians);
        double cos = Math.cos(radians);

        // Trigonometrical evaluation:
        // If cos has the larger amount, the north-south axis dominates:
        if (cos > Math.abs(sin)) {
            return BlockFace.SOUTH;
        } else if (-cos > Math.abs(sin)) {
            return BlockFace.NORTH;
        }
        // Otherwise, the east-west axis dominates:
        else if (sin > 0) {
            return BlockFace.WEST;
        } else {
            return BlockFace.EAST;
        }
    }

    public static boolean isHalfBlock(@NotNull Location location) {
        return location.y() % 1 == 0.5;
    }

    public static @NotNull BoundingBox fromLocation(@NotNull Location location, double scale) {
        final double x = location.x();
        final double y = location.y();
        final double z = location.z();

        double minY, maxY;

        if (!BlockUtil.isHalfBlock(location)) {
            minY = (y - 0.5) * scale;
            maxY = (y + 0.5) * scale;
        } else {
            minY = y * scale;
            maxY = (y + 1) * scale;
        }

        double minX = (x - 0.5) * scale;
        double minZ = (z - 0.5) * scale;
        double maxX = (x + 0.5) * scale;
        double maxZ = (z + 0.5) * scale;

        return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

}
