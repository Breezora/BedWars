package net.alphalightning.bedwars.setup.map.jackson;

import org.bukkit.Location;

public final class Lobby {

    private final Location spawnLocation;
    private final Location hologramLocation;

    public Lobby(Location spawnLocation, Location hologramLocation) {
        this.spawnLocation = spawnLocation;
        this.hologramLocation = hologramLocation;
    }

    public Location spawnLocation() {
        return spawnLocation;
    }

    public Location hologramLocation() {
        return hologramLocation;
    }
}
