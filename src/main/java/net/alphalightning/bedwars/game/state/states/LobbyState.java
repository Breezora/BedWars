package net.alphalightning.bedwars.game.state.states;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.config.Configuration;
import net.alphalightning.bedwars.game.countdown.LobbyCountdown;
import net.alphalightning.bedwars.game.map.MapManager;
import net.alphalightning.bedwars.game.state.AbstractGameState;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.alphalightning.bedwars.setup.map.jackson.GameMap;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.alphalightning.bedwars.util.PlayerUtil;
import net.breezora.celestial.DisplayType;
import net.breezora.celestial.Scoreboard;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class LobbyState extends AbstractGameState implements Listener {

    private final GameStateContext context;
    private final Configuration configuration;
    private final LobbyCountdown countdown;
    private final MapManager mapManager;
    private GameMap gameMap;

    public LobbyState(@NotNull BedWarsPlugin plugin, GameStateContext context) {
        super(context);
        this.context = context;
        this.configuration = plugin.configuration();
        this.countdown = new LobbyCountdown(plugin, context, 30);
        this.mapManager = new MapManager(plugin);

        selectMap(plugin);
        startCountdown();

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

    // --------------------- State related event logics ---------------------

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!(this.context.currentState() instanceof LobbyState)) {
            return;
        }
        Player player = event.getPlayer();

        event.joinMessage(Component.translatable("state.lobby.join",
                NamedTranslationArgument.component("name", player.displayName()),
                NamedTranslationArgument.numeric("current", Bukkit.getOnlinePlayers().size()),
                NamedTranslationArgument.numeric("max", Bukkit.getMaxPlayers())
        ));
        preparePlayer(player);
        teleportPlayer(player);
        createScoreboard(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!(this.context.currentState() instanceof LobbyState)) {
            return;
        }
        event.quitMessage(null);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(this.context.currentState() instanceof LobbyState)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (!(this.context.currentState() instanceof LobbyState)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (!(this.context.currentState() instanceof LobbyState)) {
            return;
        }
        event.setCancelled(true);
    }

    private void preparePlayer(@NotNull Player player) {
        player.setFoodLevel(20);
        player.setHealthScale(20.0D);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setGameMode(GameMode.ADVENTURE);

        if (this.countdown.isRunning()) {
            PlayerUtil.updateCountdownInformation(player, this.countdown.duration(), this.countdown.remainingTime());
        }
    }

    // --------------------- Private shit ---------------------

    private int calculateMinPlayers() {
        double factor = this.configuration.main().minPlayers();
        double calculated = Bukkit.getMaxPlayers() * factor;

        return (int) Math.floor(calculated);
    }

    private void teleportPlayer(@NotNull Player player) {
        Location location = this.context.lobbySpawn();
        if (location != null) {
            player.teleport(location);
        }
    }

    private void createScoreboard(Player player) {
        Scoreboard scoreboard = Scoreboard.builder(DisplayType.SIDEBAR)
                .player(player)
                .title(Component.translatable("state.lobby.scoreboard.title"))
                .appendLines(Arrays.asList(
                        null,
                        Component.text("Test")
                ))
                .build();

        scoreboard.display();
    }

    private void startCountdown() {
        this.context.requiredPlayers(calculateMinPlayers());
        this.countdown.start();
    }

    private void selectMap(@NotNull BedWarsPlugin plugin) {
        this.gameMap = mapManager.selectRandom();
        updateServerInfo();

        plugin.getComponentLogger().info(Component.translatable("state.lobby.map",
                NamedTranslationArgument.component("map", Component.text(gameMap.name()))));
    }

    private void updateServerInfo() {
        Bukkit.getServer().motd(Component.text(this.gameMap.name()));
        Bukkit.getServer().setMaxPlayers(this.gameMap.teams().size() * this.gameMap.teamSize());
    }
}

