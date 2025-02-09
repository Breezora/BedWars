package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.feedback.visual.Visualization;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.jetbrains.annotations.NotNull;

public class EntityVisualization implements Visualization<Location> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final EntityType entityType;
    private final MapSetup setup;
    private final Component name;

    public EntityVisualization(EntityType entityType, MapSetup setup, Component name) {
        this.entityType = entityType;
        this.setup = setup;
        this.name = name;
    }

    @Override
    public void show(@NotNull Location location) {
        final World world = location.getWorld();

        this.visualizationManager.trackEntity(this.setup, world.spawnEntity(location, this.entityType, SpawnReason.CUSTOM, entity -> {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.setAI(false);
            }
            entity.customName(this.name);
            entity.setSilent(true);
            entity.setNoPhysics(true);
            entity.setGravity(false);
            entity.setInvulnerable(true);
        }));
    }
}
