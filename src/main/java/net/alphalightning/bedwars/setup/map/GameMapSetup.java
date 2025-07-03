package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.SpawnerType;
import net.alphalightning.bedwars.setup.map.jackson.GameMap;
import net.alphalightning.bedwars.setup.map.jackson.JacksonLocation;
import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
import net.alphalightning.bedwars.setup.map.jackson.SimpleJacksonLocation;
import net.alphalightning.bedwars.setup.map.stages.CancelStage;
import net.alphalightning.bedwars.setup.map.stages.CompleteSetupStage;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.map.stages.WelcomeStage;
import net.alphalightning.bedwars.setup.map.stages.gamemap.*;
import net.alphalightning.bedwars.util.CuboidSelection;
import net.alphalightning.bedwars.util.RegionInformation;
import net.alphalightning.bedwars.util.RegionUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class GameMapSetup implements MapSetup {

    public static final int WELCOME_STAGE = 0;
    public static final int TEAM_SELECTION_STAGE = 1;
    public static final int SPAWNER_CONFIGURATION_STAGE = 2;
    public static final int TEAM_SIZE_CONFIGURATION_STAGE = 3;
    public static final int MAX_BUILD_HEIGHT_CONFIGURATION_STAGE = 4;
    public static final int MIN_BUILD_HEIGHT_CONFIGURATION_STAGE = 5;
    public static final int SPECTATOR_SPAWNPOINT_CONFIGURATION_STAGE = 6;
    public static final int EMERALD_SPAWNER_CONFIGURATION_STAGE = 7;
    public static final int DIAMOND_SPAWNER_CONFIGURATION_STAGE = 8;
    public static final int TEAM_SPAWNPOINT_CONFIGURATION_STAGE = 9;
    public static final int SLOW_IRON_CONFIGURATION_STAGE = 10;
    public static final int TEAM_LOOTSPAWNER_CONFIGURATION_STAGE = 11;
    public static final int TEAM_CHEST_CONFIGURATION_STAGE = 12;
    public static final int ITEM_SHOP_VILLAGER_CONFIGURATION_STAGE = 13;
    public static final int UPGRADE_SHOP_VILLAGER_CONFIGURATION_STAGE = 14;
    public static final int BED_CONFIGURATION_STAGE = 15;
    public static final int CUBOID_SELECTION_CONFIGURATION_STAGE = 16;
    public static final int FLOOD_FILL_CONFIGURATION_STAGE = 17;
    public static final int SPAWNER_PROTECTION_CONFIGURATION_STAGE = 18;
    public static final int COMPLETION_STAGE = 19;

    private final CancelStage cancelStage;

    // Setup handling
    private final BedWarsPlugin plugin;
    private final String fileName, binaryFileName;
    private Player player;

    private Stage currentStage;
    private int stage;

    // Configuration
    private final HashMap<SpawnerType, List<SimpleJacksonLocation>> spawner = new HashMap<>();
    private final List<JacksonTeam> teams = new ArrayList<>();
    private final List<JacksonLocation> shopVillagerLocations = new ArrayList<>();
    private final List<JacksonLocation> upgradeVillagerLocations = new ArrayList<>();
    private JacksonLocation spectatorSpawn;
    private final List<CuboidSelection> selections = new ArrayList<>();
    private final List<RegionInformation> regionInformation = new ArrayList<>();
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
        this.binaryFileName = name + ".bin";
        this.cancelStage = new CancelStage(plugin, player, this, false);
    }

    @Override
    public void invalidate() {
        player = null;
    }

    @Override
    public void startStage(int stage) {
        if (currentStage != null) {
            currentStage.unregister();
        }

        this.stage = validateStage(this.stage, stage);
        this.currentStage = switch (stage) {
            case WELCOME_STAGE -> new WelcomeStage(plugin, player, this, false);
            case TEAM_SELECTION_STAGE -> new TeamSelectionStage(plugin, player, this);
            case SPAWNER_CONFIGURATION_STAGE -> new SpawnerConfigurationStage(plugin, player, this);
            case TEAM_SIZE_CONFIGURATION_STAGE -> new TeamSizeConfigurationStage(plugin, player, this);
            case MAX_BUILD_HEIGHT_CONFIGURATION_STAGE -> new MaxBuildHeightConfigurationStage(plugin, player, this);
            case MIN_BUILD_HEIGHT_CONFIGURATION_STAGE -> new MinBuildHeightConfigurationStage(plugin, player, this);
            case SPECTATOR_SPAWNPOINT_CONFIGURATION_STAGE -> new SpectatorSpawnpointConfigurationStage(plugin, player, this);
            case EMERALD_SPAWNER_CONFIGURATION_STAGE -> new EmeraldSpawnerConfigurationStage(plugin, player, this);
            case DIAMOND_SPAWNER_CONFIGURATION_STAGE -> new DiamondSpawnerConfigurationStage(plugin, player, this);
            case TEAM_SPAWNPOINT_CONFIGURATION_STAGE -> new TeamSpawnpointConfigurationStage(plugin, player, this);
            case SLOW_IRON_CONFIGURATION_STAGE -> new SlowIronConfigurationStage(plugin, player, this);
            case TEAM_LOOTSPAWNER_CONFIGURATION_STAGE -> new TeamLootspawnerConfigurationStage(plugin, player, this);
            case TEAM_CHEST_CONFIGURATION_STAGE -> new TeamChestConfigurationStage(plugin, player, this);
            case ITEM_SHOP_VILLAGER_CONFIGURATION_STAGE -> new ShopVillagerConfigurationStage(plugin, player, this);
            case UPGRADE_SHOP_VILLAGER_CONFIGURATION_STAGE -> new UpgradeVillagerConfigurationStage(plugin, player, this);
            case BED_CONFIGURATION_STAGE -> new BedConfigurationStage(plugin, player, this);
            case CUBOID_SELECTION_CONFIGURATION_STAGE -> new CuboidConfigurationStage(plugin, player, this);
            case FLOOD_FILL_CONFIGURATION_STAGE -> new FloodFillConfigurationStage(plugin, player, this);
            case SPAWNER_PROTECTION_CONFIGURATION_STAGE -> new SpawnerProtectionConfigurationStage(plugin, player, this);
            case COMPLETION_STAGE -> new CompleteSetupStage(plugin, player, this, fileName, binaryFileName, false);
            default -> cancelStage;
        };

        if (currentStage != null) {
            currentStage.run();
        }
    }

    @Override
    public @NotNull String mapName() {
        return this.name;
    }

    @Override
    public void saveConfiguration() {
        try {
            createDirectory();

            GameMap gameMap = new GameMap(name, teamSize, minBuildHeight, maxBuildHeight, slowIron, spectatorSpawn, teams, shopVillagerLocations, upgradeVillagerLocations, spawner);
            plugin.jsonMapper().writeValue(mapsDirectory().resolve(fileName).toFile(), gameMap);

            RegionUtil.saveRegions(plugin, this, regionInformation);

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

    public List<JacksonTeam> teams() {
        return teams;
    }

    public List<CuboidSelection> selections() {
        return selections;
    }

    public List<SimpleJacksonLocation> spawner() {
        return spawner.values().stream().flatMap(List::stream).toList();
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

    public boolean hasSlowIron() {
        return slowIron;
    }

    // Start data manipulation logics

    public void configureTeams(List<JacksonTeam> teams) {
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
        this.shopVillagerLocations.addAll(locations.stream().map(JacksonLocation::new).toList());
    }

    public void configureUpgradeVillager(@NotNull List<Location> locations) {
        this.upgradeVillagerLocations.addAll(locations.stream().map(JacksonLocation::new).toList());
    }

    public void configureSelections(@NotNull List<CuboidSelection> selections) {
        this.selections.addAll(selections);
    }

    public void configureRegionInformation(@NotNull List<RegionInformation> regionInformation) {
        this.regionInformation.addAll(regionInformation);
    }
}

