package net.alphalightning.bedwars.feedback.visual.impl;

import com.destroystokyo.paper.ParticleBuilder;
import net.alphalightning.bedwars.feedback.visual.Visualization;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

public class HeightVisualization implements Visualization<Location> {

    private static final double RADIUS = 7.5D;
    private static final double SPAWN_DISTANCE = 10D;
    private static final int PARTICLE_COUNT = 150;

    private final double height;

    public HeightVisualization(double height) {
        this.height = height;
    }

    @Override
    public void show(@NotNull Location location) {
        if (Math.abs(location.getY() - height) > SPAWN_DISTANCE) { // Check if player is near the target height
            return;
        }

        for (int i = 0; i < PARTICLE_COUNT; i++) {
            // Random angles and distances in radius
            double angle = Math.random() * 2 * Math.PI;
            double distance = Math.random() * RADIUS;
            double offsetX = Math.cos(angle) * distance;
            double offsetZ = Math.sin(angle) * distance;

            Location point = new Location(location.getWorld(), location.x() + offsetX, height, location.z() + offsetZ);
            drawParticle(point);
        }
    }

    private void drawParticle(Location location) {
        new ParticleBuilder(Particle.DUST)
                .location(location)
                .color(Color.RED, SIZE)
                .spawn();
    }
}
