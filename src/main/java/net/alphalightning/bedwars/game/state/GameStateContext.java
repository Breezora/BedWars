package net.alphalightning.bedwars.game.state;

import net.alphalightning.bedwars.game.state.states.LobbyState;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;

public class GameStateContext {

    private final ComponentLogger logger;

    private final GameState[] states;
    private GameState current;

    public GameStateContext(ComponentLogger logger) {
        this.logger = logger;

        this.states = new GameState[1];
        this.states[0] = new LobbyState(this);
    }

    public void setGameState(int state) {
        if (this.current != null) {
            this.current.stop();
        }
        this.current = this.states[state];
        this.current.start();
    }

    public void stopCurrentState() {
        if(this.current != null) {
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
}
