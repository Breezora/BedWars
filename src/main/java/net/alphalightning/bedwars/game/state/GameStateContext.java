package net.alphalightning.bedwars.game.state;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.state.states.InGameState;
import net.alphalightning.bedwars.game.state.states.LobbyState;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class GameStateContext {

    private final ComponentLogger logger;

    private final GameState[] states;
    private GameState current;

    private int requiredPlayers;

    public GameStateContext(@NotNull BedWarsPlugin plugin) {
        this.logger = plugin.getComponentLogger();

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

    public void requiredPlayers(int minPlayers) {
        this.requiredPlayers = minPlayers;
    }

    public int missingPlayers() {
        int current = Bukkit.getServer().getOnlinePlayers().size();
        int minPlayers = this.requiredPlayers;

        return minPlayers - current;
    }
}
