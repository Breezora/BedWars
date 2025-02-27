package net.alphalightning.bedwars.game.state.states;

import net.alphalightning.bedwars.game.state.AbstractGameState;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.kyori.adventure.text.Component;

public class LobbyState extends AbstractGameState {

    public LobbyState(GameStateContext context) {
        super(context);
    }

    @Override
    public void start() {
        context.logger().info(Component.translatable("state.lobby.start"));
    }

    @Override
    public void stop() {
        context.logger().info(Component.translatable("state.lobby.stop"));
    }
}
