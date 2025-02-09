package net.alphalightning.bedwars.util;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public final class LocationUtil {

    public static @NotNull Location adjustedCentered(@NotNull Location location) {
        final Location centered = location.clone();
        centered.setX(location.getBlockX() + 0.5D);
        centered.setZ(location.getBlockZ() + 0.5D);

        if (BlockUtil.isHalfBlock(centered)) { // Is block a half slab
            centered.setY(0.6D);
        } else {
            centered.setY(centered.y() + 0.1D);
        }

        return centered;
    }

}
