package net.alphalightning.bedwars.game.state.states;

import net.alphalightning.bedwars.game.state.AbstractGameState;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Bukkit;

public class InGameState extends AbstractGameState {

    public InGameState(GameStateContext context) {
        super(context);
    }

    @Override
    public void start() {
        TranslatableComponent component = Component.translatable("state.ingame.start");

        Bukkit.broadcast(Component.text("Test"));

        Bukkit.broadcast(component);
        context.logger().info(component);
    }

    @Override
    public void stop() {
        context.logger().info(Component.translatable("state.ingame.stop"));
    }
}
