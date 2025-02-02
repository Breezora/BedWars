package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.SpawnerType;
import net.alphalightning.bedwars.setup.map.jackson.GameMap;
import net.alphalightning.bedwars.setup.map.jackson.JacksonLocation;
import net.alphalightning.bedwars.setup.map.jackson.SimpleJacksonLocation;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.map.stages.CancelStage;
import net.alphalightning.bedwars.setup.map.stages.CompleteSetupStage;
import net.alphalightning.bedwars.setup.map.stages.WelcomeStage;
import net.alphalightning.bedwars.setup.map.stages.gamemap.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class GameMapSetup implements MapSetup {

    private final CancelStage cancelStage;

    // Setup handling
    private final BedWarsPlugin plugin;
    private final String fileName;
    private Player player;
    private int stage;

    // Configuration
    private final HashMap<SpawnerType, List<SimpleJacksonLocation>> spawner = new HashMap<>();
    private final List<Team> teams = new ArrayList<>();
    private final List<SimpleJacksonLocation> shopVillagerLocations = new ArrayList<>();
    private final List<SimpleJacksonLocation> upgradeVillagerLocations = new ArrayList<>();
    private JacksonLocation spectatorSpawn;
    private final String name;
    private boolean slowIron;
    private int emeraldSpawnerCount = 0;
    private int diamondSpawnerCount = 0;
    private int teamSize = 0;
    private int maxBuildHeight = 0;
    private int minBuildHeight = 0;

    public GameMapSetup(BedWarsPlugin plugin, Player player, String name) {
        this.plugin = plugin;
        this.player = player;
        this.name = name;
        this.fileName = name + ".json";
        this.cancelStage = new CancelStage(plugin, player, this, false);

        startStage(0);
    }

    @Override
    public @NotNull String mapName() {
        return this.name;
    }

    @Override
    public void invalidate() {
        player = null;
    }

    @Override
    public void startStage(int stage) {
        this.stage = validateStage(this.stage, stage);
        switch (stage) {
            case 0 -> new WelcomeStage(plugin, player, this, false).run();
            case 1 -> new TeamSelectionStage(plugin, player, this).run();
            case 2 -> new SpawnerConfigurationStage(plugin, player, this).run();
            case 3 -> new TeamSizeConfigurationStage(plugin, player, this).run();
            case 4 -> new MaxBuildHeightConfigurationStage(plugin, player, this).run();
            case 5 -> new MinBuildHeightConfigurationStage(plugin, player, this).run();
            case 6 -> new SpectatorSpawnpointConfigurationStage(plugin, player, this).run();
            case 7 -> new EmeraldSpawnerConfigurationStage(plugin, player, this).run();
            case 8 -> new DiamondSpawnerConfigurationStage(plugin, player, this).run();
            case 9 -> new TeamSpawnpointConfigurationStage(plugin, player, this).run();
            case 10 -> new TeamLootspawnerConfigurationStage(plugin, player, this).run();
            case 11 -> new TeamChestConfigurationStage(plugin, player, this).run();
            case 12 -> new ShopVillagerConfigurationStage(plugin, player, this).run();
            case 13 -> new UpgradeVillagerConfigurationStage(plugin, player, this).run();
            case 14 -> new BedConfigurationStage(plugin, player, this).run();
            case 15 -> new CompleteSetupStage(plugin, player, this, fileName, false).run();
            default -> cancelStage.run();
        }
    }

    @Override
    public void saveConfiguration() {
        try {
            createDirectory();

            GameMap gameMap = new GameMap(name, teamSize, minBuildHeight, maxBuildHeight, slowIron, spectatorSpawn, teams, shopVillagerLocations, upgradeVillagerLocations, spawner);
            plugin.jsonMapper().writeValue(mapsDirectory().resolve(fileName).toFile(), gameMap);

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

    public List<Team> teams() {
        return teams;
    }

    public int emeraldSpawnerCount() {
        return emeraldSpawnerCount;
    }

    public int diamondSpawnerCount() {
        return diamondSpawnerCount;
    }

    public int maxBuildHeight() {
        return maxBuildHeight;
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

    public void configureTeamSize(int size) {
        this.teamSize = size;
    }

    public void configureMaxBuildHeight(int height) {
        this.maxBuildHeight = height;
    }

    public void configureMinBuildHeight(int height) {
        this.minBuildHeight = height;
    }

    public void configureSlowIron(Boolean slow) {
        this.slowIron = slow;
    }

    public void configureSpectatorSpawn(Location location) {
        this.spectatorSpawn = new JacksonLocation(location);
    }

    public void configureEmeraldSpawnerLocations(@NotNull List<Location> locations) {
        this.spawner.putIfAbsent(SpawnerType.EMERALD, locations.stream().map(SimpleJacksonLocation::new).toList());
    }

    public void configureDiamondSpawnerLocations(@NotNull List<Location> locations) {
        this.spawner.putIfAbsent(SpawnerType.DIAMOND, locations.stream().map(SimpleJacksonLocation::new).toList());
    }

    public void configureShopVillager(@NotNull List<Location> locations) {
        this.shopVillagerLocations.addAll(locations.stream().map(SimpleJacksonLocation::new).toList());
    }

    public void configureUpgradeVillager(@NotNull List<Location> locations) {
        this.upgradeVillagerLocations.addAll(locations.stream().map(SimpleJacksonLocation::new).toList());
    }
}

