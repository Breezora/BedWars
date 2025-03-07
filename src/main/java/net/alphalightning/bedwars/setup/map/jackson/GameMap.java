package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.alphalightning.bedwars.game.SpawnerType;

import java.util.HashMap;
import java.util.List;

public record GameMap(String name, int teamSize, int minBuildHeight, int maxBuildHeight, boolean slowIron, JacksonLocation spectatorSpawn, List<Team> teams, List<SimpleJacksonLocation> shopVillager, List<SimpleJacksonLocation> upgradeVillager, HashMap<SpawnerType, List<SimpleJacksonLocation>> spawner) {

    @JsonCreator
    public GameMap(
            @JsonProperty("name") String name,
            @JsonProperty("teamSize") int teamSize,
            @JsonProperty("minBuildHeight") int minBuildHeight,
            @JsonProperty("maxBuildHeight") int maxBuildHeight,
            @JsonProperty("slowIron") boolean slowIron,
            @JsonProperty("spectatorSpawn") JacksonLocation spectatorSpawn,
            @JsonProperty("teams") List<Team> teams,
            @JsonProperty("shopVillager") List<SimpleJacksonLocation> shopVillager,
            @JsonProperty("upgradeVillager") List<SimpleJacksonLocation> upgradeVillager,
            @JsonProperty("spawner") HashMap<SpawnerType, List<SimpleJacksonLocation>> spawner) {

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
}
