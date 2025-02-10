package net.alphalightning.bedwars.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BedUtil {

    public static @Nullable Location calculateHeadLocation(@NotNull Location bottomHalf, @NotNull BlockFace blockFace) {
        final Location topHalf = bottomHalf.clone();
        return switch (blockFace) {
            case NORTH -> topHalf.add(0, 0, -1);
            case SOUTH -> topHalf.add(0, 0, 1);
            case EAST -> topHalf.add(1, 0, 0);
            case WEST -> topHalf.add(-1, 0, 0);
            default -> null;
        };
    }

    public static float yawByBlockFace(@NotNull BlockFace blockFace) {
        return switch (blockFace) {
            case WEST  -> 90f;
            case NORTH -> 180f;
            case EAST  -> 270f;
            default -> 0f;
        };
    }

    public static @NotNull Material fromColor(int color) {
        return switch (color) {
            case 0xffffff -> Material.WHITE_BED;
            case 0xaaaaaa -> Material.LIGHT_GRAY_BED;
            case 0x555555 -> Material.GRAY_BED;
            case 0x000000 -> Material.BLACK_BED;
            case 0x783d0c -> Material.BROWN_BED;
            case 0xff5555 -> Material.RED_BED;
            case 0xff8800 -> Material.ORANGE_BED;
            case 0xffff55 -> Material.YELLOW_BED;
            case 0x55ff55 -> Material.LIME_BED;
            case 0x00aa00 -> Material.GREEN_BED;
            case 0x00aaaa -> Material.CYAN_BED;
            case 0xa3e5ff -> Material.LIGHT_BLUE_BED;
            case 0x5555ff -> Material.BLUE_BED;
            case 0xaa00aa -> Material.PURPLE_BED;
            case 0xff24fb -> Material.MAGENTA_BED;
            case 0xff6ef8 -> Material.PINK_BED;
            default -> Material.AIR;
        };
    }

}
