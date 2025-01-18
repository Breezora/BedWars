package net.alphalightning.bedwars.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record General(Environment environment) {

    @JsonCreator
    public General(@JsonProperty("environment") Environment environment) {
        this.environment = environment;
    }
}
