package net.alphalightning.bedwars.util;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PlayerUtil {

    public static void updateCountdownInformation(@NotNull Player player, int duration, int timeLeft) {
        player.setLevel(timeLeft);
        player.setExp((float) timeLeft / duration);
    }

    public static @NotNull String materialString(int color) {
        return switch (color) {
            case 0xffffff -> "WHITE";
            case 0xaaaaaa -> "LIGHT_GRAY";
            case 0x555555 -> "GRAY";
            case 0x000000 -> "BLACK";
            case 0x783d0c -> "BROWN";
            case 0xff5555 -> "RED";
            case 0xff8800 -> "ORANGE";
            case 0xffff55 -> "YELLOW";
            case 0x55ff55 -> "LIME";
            case 0x00aa00 -> "GREEN";
            case 0x00aaaa -> "CYAN";
            case 0xa3e5ff -> "LIGHT_BLUE";
            case 0x5555ff -> "BLUE";
            case 0xaa00aa -> "PURPLE";
            case 0xff24fb -> "MAGENTA";
            case 0xff6ef8 -> "PINK";
            default -> "error";
        };
    }
}
