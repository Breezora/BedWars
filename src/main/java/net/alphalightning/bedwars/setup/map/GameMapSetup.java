package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.map.stages.CancelStage;
import net.alphalightning.bedwars.setup.map.stages.gamemap.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class GameMapSetup implements MapSetup {

    private final CancelStage cancelStage;

    // Setup handling
    private final BedWarsPlugin plugin;
    private final String fileName;
    private Player player;
    private int stage;

    // Configuration
    private final List<Team> teams = new ArrayList<>();
    private final List<Location> lootSpawnerLocations = new ArrayList<>();
    private final List<Location> emeraldSpawnerLocations = new ArrayList<>();
    private final List<Location> diamondSpawnerLocations = new ArrayList<>();
    private Location spectatorSpawn;
    private int emeraldSpawnerCount = 0;
    private int diamondSpawnerCount = 0;
    private int teamSize = 0;
    private int maxBuildHeight = 0;
    private int minBuildHeight = 0;
    private boolean slowIron;
    private final List<Location> shopVillagerLocations = new ArrayList<>();
    private final List<Location> upgradeVillagerLocations = new ArrayList<>();

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
            default -> cancelStage.run();
        }
    }

    @Override
    public void saveConfiguration() {
        try {
            createDirectory();
            plugin.jsonMapper().writeValue(directory().resolve(fileName).toFile(), null);

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

    public void configureSpectatorSpawn(Location location) {
        this.spectatorSpawn = location;
    }

    public void configureEmeraldSpawnerLocations(List<Location> locations) {
        this.emeraldSpawnerLocations.addAll(locations);
    }

    public void configureDiamondSpawnerLocations(List<Location> locations) {
        this.diamondSpawnerLocations.addAll(locations);
    }

    public void configureLootSpawnerLocations(List<Location> locations) {
        this.lootSpawnerLocations.addAll(locations);
    }

    public void configureSlowIron(Boolean slow) { this.slowIron = slow; }

    public void configureShopVillager(List<Location> locations) {this.shopVillagerLocations.addAll(locations);}
    public void configureUpgradeVillager(List<Location> locations) {this.upgradeVillagerLocations.addAll(locations);}

}

