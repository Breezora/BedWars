package net.alphalightning.bedwars.game.countdown;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LobbyCountdown extends Countdown {

    public LobbyCountdown(BedWarsPlugin plugin, int seconds) {
        super(plugin, seconds);
    }

    @Override
    protected void onTick(int timeLeft) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> update(player, timeLeft));
    }

    @Override
    protected void onFinish() {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> update(player, 0));
        Bukkit.broadcast(Component.translatable("countdown.lobby.finish"));
    }

    private void update(@NotNull Player player, int timeLeft) {
        player.setLevel(timeLeft);
        player.setExp((float) timeLeft / super.duration());

    }
}
