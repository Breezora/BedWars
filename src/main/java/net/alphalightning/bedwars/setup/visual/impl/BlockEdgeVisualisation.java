package net.alphalightning.bedwars.setup.visual.impl;

import com.destroystokyo.paper.ParticleBuilder;
import net.alphalightning.bedwars.setup.visual.Visualisation;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class BlockEdgeVisualisation implements Visualisation<Block> {

    private final int color;

    public BlockEdgeVisualisation(int color) {
        this.color = color;
    }

    @Override
    public void show(@NotNull Block block) {
        final BoundingBox boundingBox = BoundingBox.of(block);
        final double minX = boundingBox.getMinX();
        final double maxX = boundingBox.getMaxX();
        final double minY = boundingBox.getMinY();
        final double maxY = boundingBox.getMaxY();
        final double minZ = boundingBox.getMinZ();
        final double maxZ = boundingBox.getMaxZ();

        final World world = block.getWorld();


        // Bottom edge
        for (double x = minX; x <= maxX; x += STEP) {
            spawnParticle(new Location(world, x, minY, minZ));
            spawnParticle(new Location(world, x, minY, maxZ));
        }
        for (double z = minZ; z <= maxZ; z += STEP) {
            spawnParticle(new Location(world, minX, minY, z));
            spawnParticle(new Location(world, maxX, minY, z));
        }

        // Top edge
        for (double x = minX; x <= maxX; x += STEP) {
            spawnParticle(new Location(world, x, maxY, minZ));
            spawnParticle(new Location(world, x, maxY, maxZ));
        }
        for (double z = minZ; z <= maxZ; z += STEP) {
            spawnParticle(new Location(world, minX, maxY, z));
            spawnParticle(new Location(world, maxX, maxY, z));
        }

        // Vertical edges
        for (double y = minY; y <= maxY; y += STEP) {
            spawnParticle(new Location(world, minX, y, minZ));
            spawnParticle(new Location(world, minX, y, maxZ));
            spawnParticle(new Location(world, maxX, y, minZ));
            spawnParticle(new Location(world, maxX, y, maxZ));
        }
    }

    private void spawnParticle(@NotNull Location location) {
        new ParticleBuilder(Particle.DUST)
                .location(location)
                .color(Color.fromRGB(color), SIZE)
                .spawn();
    }
}
