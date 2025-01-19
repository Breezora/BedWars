package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Team {

    private final String name;

    @JsonCreator
    public Team(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
