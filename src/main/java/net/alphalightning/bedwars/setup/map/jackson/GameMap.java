package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import net.alphalightning.bedwars.game.SpawnerType;

import java.util.HashMap;
import java.util.List;

public class GameMap {

    private final String name;
    private final int teamSize;
    private final int minBuildHeight;
    private final int maxBuildHeight;
    private final boolean slowIron;
    private final JacksonLocation spectatorSpawn;
    private final List<Team> teams;
    private final List<SimpleJacksonLocation> shopVillager;
    private final List<SimpleJacksonLocation> upgradeVillager;
    private final HashMap<SpawnerType, List<SimpleJacksonLocation>> spawner;

    @JsonCreator
    public GameMap(String name, int teamSize, int minBuildHeight, int maxBuildHeight, boolean slowIron, JacksonLocation spectatorSpawn, List<Team> teams, List<SimpleJacksonLocation> shopVillager, List<SimpleJacksonLocation> upgradeVillager, HashMap<SpawnerType, List<SimpleJacksonLocation>> spawner) {
        this.name = name;
        this.teamSize = teamSize;
        this.minBuildHeight = minBuildHeight;
        this.maxBuildHeight = maxBuildHeight;
        this.slowIron = slowIron;
        this.spectatorSpawn = spectatorSpawn;
        this.teams = teams;
        this.shopVillager = shopVillager;
        this.upgradeVillager = upgradeVillager;
        this.spawner = spawner;
    }

    public String name() {
        return name;
    }

    public int teamSize() {
        return teamSize;
    }

    public int minBuildHeight() {
        return minBuildHeight;
    }

    public int maxBuildHeight() {
        return maxBuildHeight;
    }

    public boolean slowIron() {
        return slowIron;
    }

    public JacksonLocation spectatorSpawn() {
        return spectatorSpawn;
    }

    public List<Team> teams() {
        return teams;
    }

    public List<SimpleJacksonLocation> shopVillager() {
        return shopVillager;
    }

    public List<SimpleJacksonLocation> upgradeVillager() {
        return upgradeVillager;
    }

    public HashMap<SpawnerType, List<SimpleJacksonLocation>> spawner() {
        return spawner;
    }
}
