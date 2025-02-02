package net.alphalightning.bedwars.manager.impl;

import net.alphalightning.bedwars.exception.AlreadyRegisteredException;
import net.alphalightning.bedwars.exception.NotRegisteredException;
import net.alphalightning.bedwars.manager.PlayerManager;
import net.alphalightning.bedwars.provider.PlayerProvider;
import net.alphalightning.bedwars.provider.impl.ConfigurationPlayerProvider;
import net.alphalightning.bedwars.setup.ConfigurationType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

public class ConfigurationPlayerManager implements PlayerManager<ConfigurationType> {

    private final Queue<Player> lobbySetupPlayers = new ConcurrentLinkedQueue<>();
    private final Queue<Player> gameMapSetupPlayers = new ConcurrentLinkedQueue<>();
    private final PlayerProvider allPlayerProvider = new ConfigurationPlayerProvider(this::mergeQueues);

    private @NotNull Stream<Player> mergeQueues() {
        Queue<Player> merged = new ConcurrentLinkedQueue<>();
        merged.addAll(lobbySetupPlayers);
        merged.addAll(gameMapSetupPlayers);

        return merged.stream();
    }

    @Override
    public @NotNull List<Player> registeredPlayers() {
        return this.mergeQueues().toList();
    }

    @Override
    public @NotNull PlayerProvider players() {
        return this.allPlayerProvider;
    }

    @Override
    public void registerPlayer(Player player, ConfigurationType type) throws AlreadyRegisteredException {
        if (this.allPlayerProvider.players().contains(player)) {
            throw new AlreadyRegisteredException();
        }

        if (type == ConfigurationType.LOBBY) {
            this.lobbySetupPlayers.add(player);
            return;
        }
        gameMapSetupPlayers.add(player);
    }

    @Override
    public void unregisterPlayer(Player player) throws NotRegisteredException {
        if (!this.allPlayerProvider.players().contains(player)) {
            throw new NotRegisteredException();
        }
        if (lobbySetupPlayers.contains(player)) {
            lobbySetupPlayers.remove(player);
            return;
        }
        gameMapSetupPlayers.remove(player);
    }
}
