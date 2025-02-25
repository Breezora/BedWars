package net.alphalightning.bedwars.game.countdown;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LobbyCountdown extends Countdown {

    public LobbyCountdown(BedWarsPlugin plugin, int seconds) {
        super(plugin, seconds);
    }

    @Override
    protected void onTick(int timeLeft) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            if (timeLeft % 5 == 0 || timeLeft == 4) {
                sendTitle(player, timeLeft, NamedTextColor.YELLOW);
            }
            switch (timeLeft) {
                case 3, 2, 1 -> sendTitle(player, timeLeft, NamedTextColor.RED);
            }
            update(player, timeLeft);
        });
    }

    @Override
    protected void onFinish() {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            update(player, 0);
            player.clearTitle();
            Feedback.pling(player);
        });
        Bukkit.broadcast(Component.translatable("countdown.lobby.finish"));
    }

    private void update(@NotNull Player player, int timeLeft) {
        player.setLevel(timeLeft);
        player.setExp((float) timeLeft / super.duration());
    }

    private void sendTitle(@NotNull Player player, int timeLeft, NamedTextColor color) {
        player.showTitle(Title.title(Component.text(timeLeft).color(color), Component.empty()));
        Feedback.lower(player);
    }
}
