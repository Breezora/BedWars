package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bukkit.Location;

public class Team {

    private final String name;
    private Location spawnpoint;

    @JsonCreator
    public Team(String name) {
        this(name, null);
    }

    @JsonCreator
    public Team(String name, Location spawnpoint) {
        this.name = name;
        this.spawnpoint = spawnpoint;
    }

    public String name() {
        return name;
    }

    public Location spawnpoint() {
        return spawnpoint;
    }

    public Team spawnpoint(Location spawnpoint) {
        this.spawnpoint = spawnpoint;
        return this;
    }

}
