package net.alphalightning.bedwars.game.state;

import org.jetbrains.annotations.NotNull;

public class GameStateContext {

    private GameState current;

    public void setGameState(@NotNull GameState state) {
        if (this.current != null) {
            this.current.stop();
        }
        this.current = state;
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
