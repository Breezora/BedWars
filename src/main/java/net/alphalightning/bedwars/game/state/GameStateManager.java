package net.alphalightning.bedwars.game.state;

public class GameStateManager {

    private static GameState currentState = GameState.LOBBY;

    public static void setGameState(GameState newState) {
        currentState = newState;
        handleStateChange(newState);
    }

    public static GameState getGameState() {
        return currentState;
    }

    private static void handleStateChange(GameState state) {
        switch (state) {
            case LOBBY-> {
                //only let players join when in this phase,
                //  except for players with
                //  permissions (i.e. mods that are watching hackers)
                //set countdown with logic of minimum players.
                //show stats
                //when countdown is at 5 seconds, disable map change.
                //copy map that got selected (either by map vote or forced) to world folder.
            }
            case IN_GAME -> {
                //teleport players to islands
                //start item spawning
                //start listening to destroyed beds
                //start countdown to beds getting destroyed
            }
            case IN_GAME_BEDS_DESTROYED -> {
                //destroy all beds.
                //start countdown to sudden death phase
            }
            case IN_GAME_SUDDEN_DEATH -> {
                //spawn enderdragons
                //disable buying new dragons in item shop
            }
            case GAME_END -> {
                //announce winner
                //send players back to lobby after like 5 seconds
                //delete world folder.
                //switch to lobby phase
            }
        }
    }
}
