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
            switch (timeLeft) {
                case 5, 4, 3, 2, 1 -> {
                    player.showTitle(Title.title(Component.text(timeLeft).color(NamedTextColor.YELLOW), Component.empty()));
                    Feedback.lower(player);
                }
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
}
