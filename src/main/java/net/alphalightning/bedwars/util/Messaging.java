package net.alphalightning.bedwars.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public final class Messaging {

    public static void sendPlayerMessage(@NotNull Player player, @NotNull String messageKey) {
        Locale locale = player.locale();
        player.sendMessage(GlobalTranslator.render(Component.translatable(messageKey), locale));
    }

}
