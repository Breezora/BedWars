package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class LobbyLocations {

    private final Map<String, SimpleJacksonLocation> locations;

    @JsonCreator
    public LobbyLocations(@JsonProperty("locations") Map<String, SimpleJacksonLocation> locations) {
        this.locations = locations;
    }

    public LobbyLocations() {
        this(new HashMap<>());
    }

    public SimpleJacksonLocation get(String name) {
        return locations.computeIfAbsent(name, _ -> new SimpleJacksonLocation());
    }
}
