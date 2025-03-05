package net.alphalightning.bedwars.util;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PlayerUtil {

    public static void updateCountdownInformation(@NotNull Player player, int duration, int timeLeft) {
        player.setLevel(timeLeft);
        player.setExp((float) timeLeft / duration);
    }

}
