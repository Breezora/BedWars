package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;

public class JacksonSpawner {

    private final int id;

    @JsonCreator
    public JacksonSpawner(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

}
