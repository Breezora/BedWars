package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.feedback.visual.Visualization;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.jetbrains.annotations.NotNull;

public class TextVisualization implements Visualization<Location> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final MapSetup setup;
    private final Component text;

    public TextVisualization(MapSetup setup, Component text) {
        this.setup = setup;
        this.text = text;
    }

    @Override
    public void show(@NotNull Location location) {
        final World world = location.getWorld();

        Entity spawnedEntity = world.spawnEntity(location, EntityType.TEXT_DISPLAY, SpawnReason.CUSTOM, entity -> {
            TextDisplay textDisplay = (TextDisplay) entity;
            textDisplay.text(this.text);
            textDisplay.setShadowed(true);
            textDisplay.setSeeThrough(false);
        });
        this.visualizationManager.trackEntity(this.setup, spawnedEntity);
    }
}
