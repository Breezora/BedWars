package net.alphalightning.bedwars.util;

import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;

public record CuboidSelection(JacksonTeam owner, Location first, Location second) {

    public @NotNull @Unmodifiable List<Block> corners() {
        return List.of(first.getBlock(), second.getBlock());
    }

    public List<Location> allBetween() {
        World world = first.getWorld();

        int minX = Math.min(first.getBlockX(), second.getBlockX());
        int maxX = Math.max(first.getBlockX(), second.getBlockX());

        int minY = Math.min(first.getBlockY(), second.getBlockY());
        int maxY = Math.max(first.getBlockY(), second.getBlockY());

        int minZ = Math.min(first.getBlockZ(), second.getBlockZ());
        int maxZ = Math.max(first.getBlockZ(), second.getBlockZ());

        List<Location> locations = new ArrayList<>();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    locations.add(new Location(world, x, y, z));
                }
            }
        }

        return locations;
    }
}
