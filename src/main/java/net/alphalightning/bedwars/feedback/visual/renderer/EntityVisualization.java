package net.alphalightning.bedwars.feedback.visual.renderer;

import net.alphalightning.bedwars.feedback.visual.Visualization;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.util.BedUtil;
import net.alphalightning.bedwars.util.BlockUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityVisualization implements Visualization<Location> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();

    private final MapSetup setup;
    private final Player player;
    private final EntityType entityType;
    private final Material material;
    private final Component text;

    public EntityVisualization(@NotNull MapSetup setup, @NotNull Player player, @NotNull EntityType entityType, @Nullable Material material, @Nullable Component text) {
        this.setup = setup;
        this.player = player;
        this.entityType = entityType;
        this.material = material;
        this.text = text;
    }

    @Override
    public void show(@NotNull Location location) {
        final World world = location.getWorld();

        if (this.entityType == EntityType.BLOCK_DISPLAY) {
            visualizeBlock(world, location);
            return;
        }

        this.visualizationManager.trackEntity(this.setup, world.spawnEntity(location, this.entityType, SpawnReason.CUSTOM, entity -> {
            entity.setSilent(true);
            entity.setNoPhysics(true);
            entity.setGravity(false);
            entity.setInvulnerable(true);

            if (entity instanceof LivingEntity living) {
                living.setAI(false);

            } else if (entity instanceof TextDisplay textDisplay) {
                if (this.text == null) {
                    throw new IllegalArgumentException();
                }
                textDisplay.text(this.text);
                textDisplay.setShadowed(true);
                textDisplay.setSeeThrough(false);

            } else {
                throw new UnsupportedOperationException();
            }
        }));
    }

    private void visualizeBlock(@NotNull World world, @NotNull Location location) {
        if (this.material == null) {
            throw new IllegalArgumentException();
        }

        final BlockFace blockFace = BlockUtil.cardinalDirection(this.player);
        final Location spawnLocation;

        if (this.material.name().contains("BED")) {
            spawnLocation = BlockUtil.correctLocation(location, blockFace, true);
        } else {
            spawnLocation = BlockUtil.correctLocation(location, blockFace, false);
        }

        this.visualizationManager.trackEntity(this.setup, world.spawnEntity(spawnLocation.toBlockLocation(), EntityType.BLOCK_DISPLAY, SpawnReason.CUSTOM, entity -> {
            final BlockDisplay blockDisplay = (BlockDisplay) entity;
            blockDisplay.setBlock(this.material.createBlockData());
            blockDisplay.setRotation(BedUtil.yawByBlockFace(this.material != Material.CHEST ? blockFace : blockFace.getOppositeFace()), 0f);

        }));
    }
}
