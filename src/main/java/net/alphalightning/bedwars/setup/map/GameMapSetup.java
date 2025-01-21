package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.SpawnerType;
import net.alphalightning.bedwars.setup.map.jackson.GameMap;
import net.alphalightning.bedwars.setup.map.jackson.JacksonSpawner;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.map.stages.CancelStage;
import net.alphalightning.bedwars.setup.map.stages.gamemap.SpawnerConfigurationStage;
import net.alphalightning.bedwars.setup.map.stages.gamemap.TeamSelectionStage;
import net.alphalightning.bedwars.setup.map.stages.gamemap.WelcomeStage;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class GameMapSetup implements MapSetup {

    private final CancelStage cancelStage;

    private final BedWarsPlugin plugin;
    private final String fileName;

    private Player player;
    private int stage;

    private final List<Team> teams = new ArrayList<>();
    private int emeraldSpawnerCount = 0;
    private int diamondSpawnerCount = 0;

    public GameMapSetup(BedWarsPlugin plugin, Player player, String name) {
        this.plugin = plugin;
        this.player = player;
        this.fileName = name + ".json";
        this.cancelStage = new CancelStage(plugin, player, this, false);

        startStage(0);
    }

    @Override
    public void invalidate() {
        player = null;
    }

    @Override
    public void startStage(int stage) {
        this.stage = validateStage(this.stage, stage);
        switch (stage) {
            case 0 -> new WelcomeStage(plugin, player, this).run();
            case 1 -> new TeamSelectionStage(plugin, player, this).run();
            case 2 -> new SpawnerConfigurationStage(plugin, player, this).run();
            default -> cancelStage.run();
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

    @Override
    public int stage() {
        return stage;
    }

    public int emeraldSpawnerCount() {
        return emeraldSpawnerCount;
    }

    public int diamondSpawnerCount() {
        return diamondSpawnerCount;
    }

    // Start data manipulation logics

    public void configureTeams(List<Team> teams) {
        this.teams.addAll(teams);
    }

    public void configureEmeraldSpawnerCount(int count) {
        this.emeraldSpawnerCount = count;
    }

    public void configureDiamondSpawnerCount(int count) {
        this.diamondSpawnerCount = count;
    }
}
