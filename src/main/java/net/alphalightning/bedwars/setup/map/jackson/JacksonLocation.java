package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bukkit.Location;

import java.util.UUID;

public class JacksonLocation extends SimpleJacksonLocation {

    private final float pitch;
    private final float yaw;

    @JsonCreator
    public JacksonLocation(UUID uuid, String world, double x, double y, double z, float pitch, float yaw) {
        super(uuid, world, x, y, z);
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public JacksonLocation(Location location) {
        this(location.getWorld().getUID(), location.getWorld().getName(), location.x(), location.y(), location.z(), location.getPitch(), location.getYaw());
    }

    public JacksonLocation() {
        this(UUID.randomUUID(), "world", 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
    }

    public float pitch() {
        return pitch;
    }

    public float yaw() {
        return yaw;
    }
}
