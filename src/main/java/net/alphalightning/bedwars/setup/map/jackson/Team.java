package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bukkit.Location;

public class Team {

    @JsonIgnore private final int color;

    private final String name;
    private JacksonLocation spawnpoint;
    private SimpleJacksonLocation chest;
    private SimpleJacksonLocation bedBottomHalf;
    private SimpleJacksonLocation bedTopHalf;
    private SimpleJacksonLocation lootspawner;

    @JsonCreator
    public Team(@JsonProperty("name") String name, int color) {
        this(name, color, null, null, null, null, null);
    }

    @JsonCreator
    public Team(
            @JsonProperty("name") String name,
            int color,
            @JsonProperty("spawnpoint") JacksonLocation spawnpoint,
            @JsonProperty("chest") SimpleJacksonLocation chest,
            @JsonProperty("bedBottomHalf") SimpleJacksonLocation bedBottomHalf,
            @JsonProperty("bedTopHalf") SimpleJacksonLocation bedTopHalf,
            @JsonProperty("lootspawner") SimpleJacksonLocation lootspawner) {

        this.color = color;
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

    public int color() {
        return color;
    }

    public JacksonLocation spawnpoint() {
        return spawnpoint;
    }

    public SimpleJacksonLocation chest() {
        return chest;
    }

    public SimpleJacksonLocation bedBottomHalf() {
        return bedBottomHalf;
    }

    public SimpleJacksonLocation bedTopHalf() {
        return bedTopHalf;
    }

    public SimpleJacksonLocation lootspawner() {
        return lootspawner;
    }

    public void bedBottomHalf(Location bedBottomHalf) {
        this.bedBottomHalf = new SimpleJacksonLocation(bedBottomHalf);
    }

    public void bedTopHalf(Location bedTopHalf) {
        this.bedTopHalf = new SimpleJacksonLocation(bedTopHalf);
    }

    public void chest(Location chest) {
        this.chest = new SimpleJacksonLocation(chest);
    }

    public void spawnpoint(Location spawnpoint) {
        this.spawnpoint = new JacksonLocation(spawnpoint);
    }

    public void lootspawner(Location lootspawner) {
        this.lootspawner = new SimpleJacksonLocation(lootspawner);
    }
}
