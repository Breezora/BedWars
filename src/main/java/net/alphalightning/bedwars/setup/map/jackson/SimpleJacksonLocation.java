package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bukkit.Location;

import java.util.UUID;

public class SimpleJacksonLocation {

    private final UUID uuid;
    private final String world;
    private final double x;
    private final double y;
    private final double z;

    @JsonCreator
    public SimpleJacksonLocation(UUID uuid, String world, double x, double y, double z) {
        this.uuid = uuid;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SimpleJacksonLocation(Location location) {
        this(location.getWorld().getUID(), location.getWorld().getName(), location.x(), location.y(), location.z());
    }

    public SimpleJacksonLocation() {
        this(UUID.randomUUID(), "world", 0.0D, 0.0D, 0.0D);
    }

    public UUID uuid() {
        return uuid;
    }

    public String world() {
        return world;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }
}
