package net.alphalightning.bedwars.feedback;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public interface Feedback {

    float VOLUME = 0.5f;
    float PITCH = 1.0f;

    static void more(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_STEP, VOLUME, PITCH);
    }

    static void lower(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, VOLUME, PITCH);
    }

    static void error(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, VOLUME, PITCH);
    }

    static void success(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, VOLUME, PITCH);
    }

    static void complete(Player player) {
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, VOLUME, PITCH);
    }

    static void click(Player player) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, VOLUME, PITCH);
    }

    static void back(Player player) {
        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, VOLUME, PITCH);
    }

}
