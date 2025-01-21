package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import net.alphalightning.bedwars.game.SpawnerType;

import java.util.HashMap;
import java.util.List;

public class GameMap {

    private final List<Team> teams;
    private final HashMap<SpawnerType, JacksonSpawner> spawner;

    @JsonCreator
    public GameMap(List<Team> teams, HashMap<SpawnerType, JacksonSpawner> spawner) {
        this.teams = teams;
        this.spawner = spawner;
    }

    public List<Team> teams() {
        return teams;
    }

    public HashMap<SpawnerType, JacksonSpawner> spawner() {
        return spawner;
    }
}
