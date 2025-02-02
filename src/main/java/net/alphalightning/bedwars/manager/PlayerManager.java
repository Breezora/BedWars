package net.alphalightning.bedwars.manager;

import net.alphalightning.bedwars.exception.AlreadyRegisteredException;
import net.alphalightning.bedwars.exception.NotRegisteredException;
import net.alphalightning.bedwars.provider.PlayerProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface PlayerManager<T> {

    @NotNull List<Player> registeredPlayers();

    @NotNull PlayerProvider players();

    void registerPlayer(Player player, T t) throws AlreadyRegisteredException;

    void unregisterPlayer(Player player) throws NotRegisteredException;

}
