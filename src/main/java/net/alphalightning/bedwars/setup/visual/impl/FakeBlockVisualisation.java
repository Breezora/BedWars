package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.setup.visual.Visualisation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.jetbrains.annotations.NotNull;

public class FakeBlockVisualisation implements Visualisation<Location> {

    private final Material material;

    public FakeBlockVisualisation(Material material) {
        this.material = material;
    }

    @Override
    public void show(@NotNull Location location) {
        final Location blockLocation = location.toBlockLocation();
        final World world = location.getWorld();

        world.spawnEntity(location, EntityType.BLOCK_DISPLAY, SpawnReason.CUSTOM, entity -> {
            final BlockDisplay blockDisplay = (BlockDisplay) entity;
            final Location displayLocation = blockLocation.addRotation(location.getYaw() * -1, location.getPitch() * -1);

            blockDisplay.setBlock(Bukkit.createBlockData(material));
            blockDisplay.teleport(displayLocation);
        });
    }
}
