package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.feedback.visual.Visualization;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.jetbrains.annotations.NotNull;

public class EntityVisualization implements Visualization<Location> {

    private final EntityType entityType;
    private final Component name;

    public EntityVisualization(EntityType entityType, Component name) {
        this.entityType = entityType;
        this.name = name;
    }

    @Override
    public void show(@NotNull Location location) {
        final World world = location.getWorld();

        world.spawnEntity(location, this.entityType, SpawnReason.CUSTOM, entity -> {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.setAI(false);
            }
            entity.customName(this.name);
            entity.setGravity(false);
            entity.setNoPhysics(true);
            entity.setSilent(true);
        });
    }
}
