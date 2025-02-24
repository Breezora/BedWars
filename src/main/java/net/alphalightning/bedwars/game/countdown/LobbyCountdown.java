package net.alphalightning.bedwars.game.countdown;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public class LobbyCountdown extends Countdown {

    public LobbyCountdown(BedWarsPlugin plugin, int seconds) {
        super(plugin, seconds);
    }

    @Override
    protected void onTick(int timeLeft) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            player.setLevel(timeLeft);
            player.setExp((float) timeLeft / super.seconds());
        });
    }

    @Override
    protected void onFinish() {
        Bukkit.broadcast(Component.translatable("countdown.lobby.finish"));
    }
}
