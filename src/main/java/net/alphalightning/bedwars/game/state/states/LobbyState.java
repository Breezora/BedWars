package net.alphalightning.bedwars.game.state.states;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.countdown.LobbyCountdown;
import net.alphalightning.bedwars.game.state.AbstractGameState;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class LobbyState extends AbstractGameState {

    private final LobbyCountdown countdown;

    public LobbyState(@NotNull BedWarsPlugin plugin) {
        super(plugin.gameStateContext());
        this.countdown = new LobbyCountdown(plugin, 30);
    }

    @Override
    public void start() {
        this.countdown.start();
        context.logger().info(Component.translatable("state.lobby.start"));
    }

    @Override
    public void stop() {
        this.countdown.cancel();
        context.logger().info(Component.translatable("state.lobby.stop"));
    }
}
