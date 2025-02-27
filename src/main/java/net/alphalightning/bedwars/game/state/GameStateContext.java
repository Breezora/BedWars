package net.alphalightning.bedwars.game.state;

import net.alphalightning.bedwars.game.state.states.LobbyState;
import org.jetbrains.annotations.NotNull;

public class GameStateContext {

    private GameState current;
    private final GameState[] states;

    public GameStateContext() {
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

}
