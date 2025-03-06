package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class LobbyLocations {

    private final Map<String, JacksonLocation> locations;

    @JsonCreator
    public LobbyLocations(@JsonProperty("locations") Map<String, JacksonLocation> locations) {
        this.locations = locations;
    }

    public JacksonLocation get(String name) {
        return locations.computeIfAbsent(name, _ -> new JacksonLocation());
    }
}
