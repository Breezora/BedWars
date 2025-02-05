package net.alphalightning.bedwars.setup.visual;

import com.destroystokyo.paper.ParticleBuilder;
import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class BlockVisualizer implements Visualizer<Block> {

    private final BedWarsPlugin plugin;
    private final int color;

    public BlockVisualizer(@NotNull BedWarsPlugin plugin, int color) {
        this.plugin = plugin;
        this.color = color;
    }

    @Override
    public void visualize(@NotNull Block block) {
        Bukkit.getScheduler().runTaskTimer(this.plugin, () -> renderEdges(BoundingBox.of(block), block.getWorld()), 0L, 5L);
    }

    private void renderEdges(@NotNull BoundingBox boundingBox, World world) {
        final double step = 0.15; // Distance between particles

        final double minX = boundingBox.getMinX();
        final double maxX = boundingBox.getMaxX();
        final double minY = boundingBox.getMinY();
        final double maxY = boundingBox.getMaxY();
        final double minZ = boundingBox.getMinZ();
        final double maxZ = boundingBox.getMaxZ();

        // Bottom edge
        for (double x = minX; x <= maxX; x += step) {
            spawnVisual(new Location(world, x, minY, minZ));
            spawnVisual(new Location(world, x, minY, maxZ));
        }
        for (double z = minZ; z <= maxZ; z += step) {
            spawnVisual(new Location(world, minX, minY, z));
            spawnVisual(new Location(world, maxX, minY, z));
        }

        // Top edge
        for (double x = minX; x <= maxX; x += step) {
            spawnVisual(new Location(world, x, maxY, minZ));
            spawnVisual(new Location(world, x, maxY, maxZ));
        }
        for (double z = minZ; z <= maxZ; z += step) {
            spawnVisual(new Location(world, minX, maxY, z));
            spawnVisual(new Location(world, maxX, maxY, z));
        }

        // Vertical edges
        for (double y = minY; y <= maxY; y += step) {
            spawnVisual(new Location(world, minX, y, minZ));
            spawnVisual(new Location(world, minX, y, maxZ));
            spawnVisual(new Location(world, maxX, y, minZ));
            spawnVisual(new Location(world, maxX, y, maxZ));
        }
    }

    @Override
    public void spawnVisual(@NotNull Location location) {
        new ParticleBuilder(Particle.DUST)
                .location(location)
                .color(Color.fromRGB(color), 0.75F)
                .spawn();
    }
}
