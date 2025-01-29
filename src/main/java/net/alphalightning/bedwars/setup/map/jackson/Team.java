package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bukkit.Location;

public class Team {

    private final String name;
    private Location spawnpoint;
    private Location chest;
    private Location bed_downside;
    private Location bed_upside;

    @JsonCreator
    public Team(String name) {
        this(name, null, null, null, null);
    }

    @JsonCreator
    public Team(String name, Location spawnpoint, Location chest, Location bed_downside, Location bed_upside) {
        this.name = name;
        this.spawnpoint = spawnpoint;
        this.chest = chest;
        this.bed_downside = bed_downside;
        this.bed_upside = bed_upside;
    }

    public String name() {
        return name;
    }

    public Location spawnpoint() {
        return spawnpoint;
    }

    public Location chest() { return chest; }

    public Location bed_downside() { return bed_downside; }

    public Location bed_upside() { return bed_upside; }

    public Team bed_downside(Location bed_downside) {
        this.bed_downside = bed_downside;
        return this;
    }

    public Team bed_upside(Location bed_upside) {
        this.bed_upside = bed_upside;
        return this;
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
