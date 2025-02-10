package net.alphalightning.bedwars.feedback.visual;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public interface BlockVisualization<T> extends Visualization<T> {

    default void visualizeBoundingBox(@NotNull World world, @NotNull BoundingBox boundingBox, int color) {
        final double minX = boundingBox.getMinX();
        final double maxX = boundingBox.getMaxX();
        final double minY = boundingBox.getMinY();
        final double maxY = boundingBox.getMaxY();
        final double minZ = boundingBox.getMinZ();
        final double maxZ = boundingBox.getMaxZ();


        for (double x = minX; x <= maxX; x += STEP) {
            spawnParticle(new Location(world, x, minY, minZ), color);
            spawnParticle(new Location(world, x, minY, maxZ), color);
        }
        for (double z = minZ; z <= maxZ; z += STEP) {
            spawnParticle(new Location(world, minX, minY, z), color);
            spawnParticle(new Location(world, maxX, minY, z), color);
        }

        // Top edge
        for (double x = minX; x <= maxX; x += STEP) {
            spawnParticle(new Location(world, x, maxY, minZ), color);
            spawnParticle(new Location(world, x, maxY, maxZ), color);
        }
        for (double z = minZ; z <= maxZ; z += STEP) {
            spawnParticle(new Location(world, minX, maxY, z), color);
            spawnParticle(new Location(world, maxX, maxY, z), color);
        }

        // Vertical edges
        for (double y = minY; y <= maxY; y += STEP) {
            spawnParticle(new Location(world, minX, y, minZ), color);
            spawnParticle(new Location(world, minX, y, maxZ), color);
            spawnParticle(new Location(world, maxX, y, minZ), color);
            spawnParticle(new Location(world, maxX, y, maxZ), color);
        }
    }

    default void spawnParticle(@NotNull Location location, int color) {
        new ParticleBuilder(Particle.DUST)
                .location(location)
                .color(Color.fromRGB(color), SIZE)
                .spawn();
    }

}
