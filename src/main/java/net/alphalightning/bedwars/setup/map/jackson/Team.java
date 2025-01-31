package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bukkit.Location;

public class Team {

    private final String name;
    private Location spawnpoint;
    private Location chest;

    @JsonCreator
    public Team(String name) {
        this(name, null, null);
    }

    @JsonCreator
    public Team(String name, Location spawnpoint, Location chest) {
        this.name = name;
        this.spawnpoint = spawnpoint;
        this.chest = chest;
    }

    public String name() {
        return name;
    }

    public Location spawnpoint() {
        return spawnpoint;
    }

    public Location chest() {
        return chest;
    }

    public Team chest(Location chest) {
        this.chest = chest;
        return this;
    }

    public Team spawnpoint(Location spawnpoint) {
        this.spawnpoint = spawnpoint;
        return this;
    }

}
