package net.alphalightning.bedwars.setup.map;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.game.SpawnerType;
import net.alphalightning.bedwars.setup.map.jackson.GameMap;
import net.alphalightning.bedwars.setup.map.jackson.JacksonSpawner;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.ui.GameMapConfigurationOverviewGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class GameMapSetup implements MapSetup, Listener {

    private final BedWarsPlugin plugin;
    private final String fileName;

    private Player player;
    private int stage;

    private final List<Team> teams = new ArrayList<>();
    private int emeraldSpawnerCount = 0;

    public GameMapSetup(BedWarsPlugin plugin, Player player, String name) {
        this.plugin = plugin;
        this.player = player;
        this.fileName = name + ".json";

        startStage(0);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void invalidate() {
        player = null;
    }

    @Override
    public void startStage(int stage) {
        this.stage = validateStage(this.stage, stage);
        switch (stage) {
            case 0 -> {
                Feedback.start(player);
                player.sendMessage(Component.translatable("mapsetup.start"));
            }
            case 1 -> {
                new GameMapConfigurationOverviewGui(player, this).showGui();
                player.sendMessage(Component.translatable("mapsetup.stage.1"));
            }

            case 2 -> player.sendMessage(Component.translatable("mapsetup.stage.2"));
            default -> {
                Feedback.error(player);

                Component component = Component.translatable("mapsetup.cancel");
                player.sendMessage(component);
                plugin.getComponentLogger().warn(component);
            }
        }
    }

    @Override
    public void saveConfiguration() {
        try {
            createDirectory();
            HashMap<SpawnerType, JacksonSpawner> spawner = new HashMap<>();
            for (int i = 0; i < emeraldSpawnerCount; i++) {
                spawner.putIfAbsent(SpawnerType.EMERALD, new JacksonSpawner(i));
            }

            GameMap gameMap = new GameMap(teams, spawner);

            plugin.jsonMapper().writeValue(directory().resolve(fileName).toFile(), gameMap);
        } catch (IOException exception) {
            plugin.getLogger().severe("Could not save file " + fileName + ": " + exception.getMessage());
        }
    }

    // Start data exposure

    @Override
    public BedWarsPlugin plugin() {
        return plugin;
    }

    public int stage() {
        return stage;
    }

    public List<Team> teams() {
        return teams;
    }

    public int emeraldSpawnerCount() {
        return emeraldSpawnerCount;
    }

    // Start data manipulation logics

    public void configureTeams(List<Team> teams) {
        this.teams.addAll(teams);
    }

    public void configureEmeraldSpawnerCount(int count) {
        this.emeraldSpawnerCount = count;
    }

    // Start cancellation logic

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (player == null || !player.equals(event.getPlayer())) {
            return;
        }

        if (event.signedMessage().message().equalsIgnoreCase(CANCEL_MESSAGE)) {
            event.setCancelled(true);
            cancel();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (player != null && player.equals(event.getPlayer())) {
            cancel();
        }
    }
}
