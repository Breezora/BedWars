package net.alphalightning.bedwars.game.state.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RandomizedPremiumJoin extends PremiumJoin {

    private final List<Player> list = new ArrayList<>();

    @Override
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission(PREMIUM_PERMISSION)) {
            list.add(player);
        }
    }

    @Override
    public void onQuit(PlayerQuitEvent event) {
        list.remove(event.getPlayer());
    }

    @Override
    public Player findKickablePlayer() {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

}
