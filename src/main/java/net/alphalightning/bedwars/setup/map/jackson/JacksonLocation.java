package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

public class JacksonLocation extends SimpleJacksonLocation {

    private final float pitch;
    private final float yaw;

    @JsonCreator
    public JacksonLocation(
            @JsonProperty("world") String world,
            @JsonProperty("x") double x,
            @JsonProperty("y") double y,
            @JsonProperty("z") double z,
            @JsonProperty("pitch") float pitch,
            @JsonProperty("yaw") float yaw) {

        super(world, x, y, z);
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public JacksonLocation(Location location) {
        this(location.getWorld().getName(), location.x(), location.y(), location.z(), location.getPitch(), location.getYaw());
    }

    public JacksonLocation() {
        this("world", 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
    }

    public float pitch() {
        return pitch;
    }

    public float yaw() {
        return yaw;
    }

    @Override
    public @Nullable Location asBukkitLocation() {
        World world = Bukkit.getWorld(super.world());
        if (world == null) {
            return null;
        }
        return new Location(world, super.x(), super.y(), super.z(), yaw, pitch);
    }
}
