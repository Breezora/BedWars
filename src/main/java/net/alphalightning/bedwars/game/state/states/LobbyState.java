package net.alphalightning.bedwars.game.state.states;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.config.Configuration;
import net.alphalightning.bedwars.game.countdown.LobbyCountdown;
import net.alphalightning.bedwars.game.state.AbstractGameState;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.alphalightning.bedwars.setup.map.jackson.LobbyLocations;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.alphalightning.bedwars.util.PlayerUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static net.alphalightning.bedwars.setup.map.LobbyConfiguration.LOBBY_FILE_NAME;

public class LobbyState extends AbstractGameState implements Listener {

    private static final int MAX_PLAYERS = 4; //TODO: Make this dynamic based on selected map

    private final BedWarsPlugin plugin;
    private final GameStateContext context;
    private final Configuration configuration;
    private final LobbyCountdown countdown;

    public LobbyState(@NotNull BedWarsPlugin plugin, GameStateContext context) {
        super(context);
        this.plugin = plugin;
        this.context = context;
        this.configuration = plugin.configuration();
        this.countdown = new LobbyCountdown(plugin, context, 30);

        context.requiredPlayers(calculateMinPlayers());
        countdown.start();

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

        Player player = event.getPlayer();

        event.joinMessage(Component.translatable("state.lobby.join",
                NamedTranslationArgument.component("name", player.displayName()),
                NamedTranslationArgument.numeric("current", Bukkit.getOnlinePlayers().size()),
                NamedTranslationArgument.numeric("max", MAX_PLAYERS)
        ));
        preparePlayer(player);
        teleportPlayer(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!(this.context.currentState() instanceof LobbyState)) {
            return;
        }
        event.quitMessage(null);
    }

    private void preparePlayer(@NotNull Player player) {
        player.setFoodLevel(20);
        player.setHealthScale(20.0D);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setGameMode(GameMode.ADVENTURE);

        PlayerUtil.updateCountdownInformation(player, this.countdown.duration(), this.countdown.remainingTime());
    }

    private int calculateMinPlayers() {
        double factor = this.configuration.main().minPlayers();
        double calculated = MAX_PLAYERS * factor;

        return (int) Math.floor(calculated);
    }

    private void teleportPlayer(Player player) {
        try {
            File lobbyFile = plugin.getDataFolder().toPath().resolve("maps").resolve("lobby.json").toFile();

            LobbyLocations lobbyLocations = plugin.jsonMapper().readValue(lobbyFile, LobbyLocations.class);
            Location spawn = lobbyLocations.get("spawn").asBukkitLocation();

            if (spawn == null) {
                return;
            }
            player.teleport(spawn);

        } catch (IOException exception) {
            plugin.getLogger().severe("Could not read file " + LOBBY_FILE_NAME + ": " + exception.getMessage());
        }
    }
}

