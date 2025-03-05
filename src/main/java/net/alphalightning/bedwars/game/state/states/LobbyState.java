package net.alphalightning.bedwars.game.state.states;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.config.Configuration;
import net.alphalightning.bedwars.game.countdown.LobbyCountdown;
import net.alphalightning.bedwars.game.state.AbstractGameState;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class LobbyState extends AbstractGameState implements Listener {

    private static final int MAX_PLAYERS = 4; //TODO: Make this dynamic based on selected map

    private final GameStateContext context;
    private final Configuration configuration;
    private final LobbyCountdown countdown;
    private final int minPlayers;

    public LobbyState(@NotNull BedWarsPlugin plugin, GameStateContext context) {
        super(context);
        this.context = context;
        this.configuration = plugin.configuration();
        this.countdown = new LobbyCountdown(plugin, 30);
        this.minPlayers = calculateMinPlayers();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void start() {
        context.logger().info(Component.translatable("state.lobby.start"));
    }

    @Override
    public void stop() {
        this.countdown.cancel();
        context.logger().info(Component.translatable("state.lobby.stop"));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!(this.context.currentState() instanceof LobbyState)) {
            return;
        }

        int currentPlayers = Bukkit.getServer().getOnlinePlayers().size();

        if (currentPlayers >= this.minPlayers) {
            this.countdown.start();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        int currentPlayers = Bukkit.getServer().getOnlinePlayers().size();

        if (currentPlayers < this.minPlayers) {
            this.countdown.cancel();
        }
    }

    private int calculateMinPlayers() {
        double factor = this.configuration.main().minPlayers();
        double calculated = MAX_PLAYERS * factor;

        return (int) Math.floor(calculated);
    }
}
