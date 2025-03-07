package net.alphalightning.bedwars.game.state;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.state.states.InGameState;
import net.alphalightning.bedwars.game.state.states.LobbyState;
import net.alphalightning.bedwars.setup.map.LobbyConfiguration;
import net.alphalightning.bedwars.setup.map.jackson.LobbyLocations;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;


public class GameStateContext {

    private final ComponentLogger logger;

    private final GameState[] states;
    private GameState current;

    private final Location lobbySpawn;
    private int requiredPlayers;

    public GameStateContext(@NotNull BedWarsPlugin plugin) {
        this.logger = plugin.getComponentLogger();
        this.lobbySpawn = loadLobbySpawn(plugin);

        if (lobbySpawn == null) {
            logger.warn(Component.translatable("state.lobby.spawn"));
        }

        this.states = new GameState[2];
        this.states[0] = new LobbyState(plugin, this);
        this.states[1] = new InGameState(this);
    }

    public void setGameState(int state) {
        stopCurrentState();

        this.current = this.states[state];
        this.current.start();
    }

    private void stopCurrentState() {
        if (this.current != null) {
            this.current.stop();
            this.current = null;
        }
    }

    public @NotNull GameState currentState() {
        return this.current;
    }

    public @NotNull ComponentLogger logger() {
        return logger;
    }

    public Location lobbySpawn() {
        return lobbySpawn;
    }

    public void requiredPlayers(int minPlayers) {
        this.requiredPlayers = minPlayers;
    }

    public int missingPlayers() {
        int current = Bukkit.getServer().getOnlinePlayers().size();
        int minPlayers = this.requiredPlayers;

        return minPlayers - current;
    }

    private @Nullable Location loadLobbySpawn(@NotNull BedWarsPlugin plugin) {
        try {
            File file = plugin.getDataFolder().toPath().resolve("maps").resolve(LobbyConfiguration.LOBBY_FILE_NAME).toFile();
            LobbyLocations lobbyLocations = plugin.jsonMapper().readValue(file, LobbyLocations.class);

            return lobbyLocations.get("spawn").asBukkitLocation();

        } catch (IOException exception) {
            plugin.getLogger().severe("Could not read file " + LobbyConfiguration.LOBBY_FILE_NAME + ": " + exception.getMessage());
        }
        return null;
    }
}
