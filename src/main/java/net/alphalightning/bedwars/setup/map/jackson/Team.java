package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bukkit.Location;

public class Team {

    private final String name;
    private Location spawnpoint;
    private Location chest;
    private Location bedDownside;
    private Location bedUpside;
    private Location lootspawner;

    @JsonCreator
    public Team(String name) {
        this(name, null, null, null, null, null);
    }

    @JsonCreator
    public Team(String name, Location spawnpoint, Location chest, Location bedDownside, Location bedUpside, Location lootspawner) {
        this.name = name;
        this.spawnpoint = spawnpoint;
        this.chest = chest;
        this.bedDownside = bedDownside;
        this.bedUpside = bedUpside;
        this.lootspawner = lootspawner;
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

    public Location bedDownside() {
        return bedDownside;
    }

    public Location bedUpside() {
        return bedUpside;
    }

    public Location lootspawner() {
        return lootspawner;
    }

    public Team bedDownside(Location bedDownside) {
        this.bedDownside = bedDownside;
        return this;
    }

    public void bedUpside(Location bedUpside) {
        this.bedUpside = bedUpside;
    }

    public void chest(Location chest) {
        this.chest = chest;
    }

    public void spawnpoint(Location spawnpoint) {
        this.spawnpoint = spawnpoint;
    }

    public void lootspawner(Location lootspawner) {
        this.lootspawner = lootspawner;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", spawnpoint=" + spawnpoint +
                ", chest=" + chest +
                ", bedDownside=" + bedDownside +
                ", bedUpside=" + bedUpside +
                ", lootspawner=" + lootspawner +
                '}';
    }
}
