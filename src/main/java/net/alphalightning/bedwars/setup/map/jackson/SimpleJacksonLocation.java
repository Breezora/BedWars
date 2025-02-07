package net.alphalightning.bedwars.setup.map.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

public class SimpleJacksonLocation {

    private final String world;
    private final double x;
    private final double y;
    private final double z;

    @JsonCreator
    public SimpleJacksonLocation(String world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SimpleJacksonLocation(Location location) {
        this(location.getWorld().getName(), location.getBlockX() + 0.5D, location.y(), location.getBlockZ() + 0.5D);
    }

    public SimpleJacksonLocation() {
        this("world", 0.0D, 0.0D, 0.0D);
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

    public @Nullable Location asBukkitLocation() {
        World world = Bukkit.getWorld(this.world);
        if (world == null) {
            return null;
        }
        return new Location(world, x, y, z);
    }
}
