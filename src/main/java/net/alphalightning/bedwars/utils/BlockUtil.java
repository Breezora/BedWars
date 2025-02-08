package net.alphalightning.bedwars.utils;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
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
}
