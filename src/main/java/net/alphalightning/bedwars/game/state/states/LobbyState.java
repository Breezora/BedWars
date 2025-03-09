package net.alphalightning.bedwars.game.state.states;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.config.Configuration;
import net.alphalightning.bedwars.game.countdown.CountdownListener;
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
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.translation.GlobalTranslator;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LobbyState extends AbstractGameState implements Listener, CountdownListener {

    private final Map<Player, Scoreboard> scoreboards = new HashMap<>();

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
        this.context.logger().info(Component.translatable("state.lobby.start"));
    }

    @Override
    public void stop() {
        this.countdown.cancel();
        this.scoreboards.values().forEach(Scoreboard::destroy);
        this.context.logger().info(Component.translatable("state.lobby.stop"));
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
        updatePlayerCount();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!(this.context.currentState() instanceof LobbyState)) {
            return;
        }

        Player player = event.getPlayer();

        event.quitMessage(null);
        this.scoreboards.get(player).destroy();
        this.scoreboards.remove(player);
        Bukkit.getScheduler().runTaskLater(this.configuration.plugin(), this::updatePlayerCount, 1L);
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

    // --------------------- Countdown listener hook ---------------------

    @Override
    public void onTick(int timeLeft) {
        updateScoreboard(4, Component.translatable("state.lobby.scoreboard.countdown.running",
                NamedTranslationArgument.numeric("time", this.countdown.remainingTime()))
        );
    }

    @Override
    public void onAbort() {
        updateScoreboard(4, Component.translatable("state.lobby.scoreboard.countdown.idle"));
    }

    // --------------------- Exposure ---------------------

    public @NotNull MapManager mapManager() {
        return mapManager;
    }

    public void updateSelectedMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void updateMapName(@NotNull LobbyState lobbyState) {
        updateScoreboard(1, Component.translatable("state.lobby.scoreboard.map",
                NamedTranslationArgument.component("name", Component.text(lobbyState.mapManager().selected().name()))
        ));
    }

    // --------------------- Private shit ---------------------

    private void preparePlayer(@NotNull Player player) {
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        player.setHealthScale(20.0D);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setGameMode(GameMode.ADVENTURE);

        if (this.countdown.isRunning()) {
            PlayerUtil.updateCountdownInformation(player, this.countdown.duration(), this.countdown.remainingTime());
        }
    }

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

    private void createScoreboard(@NotNull Player player) {
        if (this.gameMap == null) {
            return;
        }

        final Locale locale = player.locale();
        Scoreboard scoreboard = Scoreboard.builder(DisplayType.SIDEBAR)
                .player(player)
                .title(Component.translatable("state.lobby.scoreboard.title"))
                .appendLines(Arrays.asList(
                        Component.empty(),
                        render(Component.translatable("state.lobby.scoreboard.map",
                                NamedTranslationArgument.component("name", Component.text(this.gameMap.name()))
                        ), locale),
                        render(Component.translatable("state.lobby.scoreboard.players",
                                NamedTranslationArgument.numeric("current", Bukkit.getOnlinePlayers().size()),
                                NamedTranslationArgument.numeric("max", Bukkit.getMaxPlayers())
                        ), locale),
                        Component.empty(),
                        render(!this.countdown.isRunning()
                                ? Component.translatable("state.lobby.scoreboard.countdown.idle")
                                : Component.translatable("state.lobby.scoreboard.countdown.running",
                                NamedTranslationArgument.numeric("time", this.countdown.remainingTime())
                        ), locale),
                        Component.empty(),
                        render(Component.translatable("state.lobby.scoreboard.matchmaking",
                                NamedTranslationArgument.component("matchmaking", Component.text(this.configuration.main().matchmaking()))
                        ), locale),
                        Component.empty(),
                        render(Component.translatable("state.lobby.scoreboard.url"), locale)
                ))
                .build();

        scoreboard.display();
        this.scoreboards.put(player, scoreboard);
    }

    private void startCountdown() {
        this.context.requiredPlayers(calculateMinPlayers());
        this.countdown.registerListener(this);
        this.countdown.start();
    }

    private void selectMap(@NotNull BedWarsPlugin plugin) {
        if ((this.gameMap = this.mapManager.selectRandom()) == null) {
            return;
        }
        plugin.getComponentLogger().info(Component.translatable("state.lobby.map", NamedTranslationArgument.component("map", Component.text(this.gameMap.name()))));
    }

    private @NotNull Component render(TranslatableComponent component, Locale locale) {
        return GlobalTranslator.render(component, locale);
    }

    private void updateScoreboard(int line, TranslatableComponent component) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = this.scoreboards.get(player);

            if (scoreboard == null) {
                createScoreboard(player);
                continue;
            }

            scoreboard.updateLine(line, render(component, player.locale()));
        }
    }

    private void updatePlayerCount() {
        updateScoreboard(2, Component.translatable("state.lobby.scoreboard.players",
                NamedTranslationArgument.numeric("current", Bukkit.getOnlinePlayers().size()),
                NamedTranslationArgument.numeric("max", Bukkit.getMaxPlayers()))
        );
    }
}
