package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bukkit.Location;

public class Team {

    private final String name;
    private Location spawnpoint;
    private Location chest;
    private Location bedBottomHalf;
    private Location bedTopHalf;
    private Location lootspawner;

    @JsonCreator
    public Team(String name) {
        this(name, null, null, null, null, null);
    }

    @JsonCreator
    public Team(String name, Location spawnpoint, Location chest, Location bedBottomHalf, Location bedTopHalf, Location lootspawner) {
        this.name = name;
        this.spawnpoint = spawnpoint;
        this.chest = chest;
        this.bedBottomHalf = bedBottomHalf;
        this.bedTopHalf = bedTopHalf;
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

    public Location bedBottomHalf() {
        return bedBottomHalf;
    }

    public Location bedTopHalf() {
        return bedTopHalf;
    }

    public Location lootspawner() {
        return lootspawner;
    }

    public Team bedBottomHalf(Location bedBottomHalf) {
        this.bedBottomHalf = bedBottomHalf;
        return this;
    }

    public void bedTopHalf(Location bedTopHalf) {
        this.bedTopHalf = bedTopHalf;
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
                ", bedBottomHalf=" + bedBottomHalf +
                ", bedTopHalf=" + bedTopHalf +
                ", lootspawner=" + lootspawner +
                '}';
    }
}
