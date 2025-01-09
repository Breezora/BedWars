package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class LobbyLocations {

    private final Map<String, Location> locations;

    @JsonCreator
    public LobbyLocations(@JsonProperty("locations") Map<String, Location> locations) {
        this.locations = locations;
    }

    public LobbyLocations() {
        this(new HashMap<>());
    }

    public Location get(String name) {
        return locations.computeIfAbsent(name, _ -> new Location(Bukkit.getWorld("world"), 0, 0, 0));
    }
}
