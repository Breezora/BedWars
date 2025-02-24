package net.alphalightning.bedwars.game.countdown;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.kyori.adventure.text.Component;
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
                case 5, 4, 3, 2, 1 -> player.showTitle(Title.title(Component.text(timeLeft), Component.empty()));
            }

            update(player, timeLeft);
            Feedback.lower(player);
        });
    }

    @Override
    protected void onFinish() {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            update(player, 0);
            Feedback.pling(player);
        });
        Bukkit.broadcast(Component.translatable("countdown.lobby.finish"));
    }

    private void update(@NotNull Player player, int timeLeft) {
        player.setLevel(timeLeft);
        player.setExp((float) timeLeft / super.duration());
    }
}
