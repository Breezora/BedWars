package net.alphalightning.bedwars.setup.visual.impl;

import com.destroystokyo.paper.ParticleBuilder;
import net.alphalightning.bedwars.setup.visual.Visualisation;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public record SingleLineVisualisation(Player player) implements Visualisation<Location> {

    private static final int LINE_LENGTH = 5;
    private static final int COLOR = 0xff5286;

    @Override
    public void show(@NotNull Location start) {
        final Vector direction = start.getDirection();
        for (double covered = 0; covered <= LINE_LENGTH; covered += STEP) {
            Location point = start.clone().add(direction.clone().multiply(covered));
            drawParticle(point);
        }
    }

    private void drawParticle(Location location) {
        new ParticleBuilder(Particle.DUST)
                .location(location)
                .color(Color.fromRGB(COLOR), SIZE)
                .spawn();
    }
}
