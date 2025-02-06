package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.setup.visual.Visualization;
import net.alphalightning.bedwars.utils.BedUtils;
import net.alphalightning.bedwars.utils.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.jetbrains.annotations.NotNull;

public class FakeBlockVisualization implements Visualization<Location> {

    private final Player player;
    private final Material material;

    public FakeBlockVisualization(Player player, Material material) {
        this.player = player;
        this.material = material;
    }

    @Override
    public void show(@NotNull Location location) {
        final World world = location.getWorld();
        final BlockFace blockFace = BlockUtil.cardinalDirection(this.player);

        if (material.name().contains("BED")) {
            location = correctLocation(location, blockFace);
        }

        world.spawnEntity(location.toBlockLocation(), EntityType.BLOCK_DISPLAY, SpawnReason.CUSTOM, entity -> {
            final BlockDisplay blockDisplay = (BlockDisplay) entity;
            blockDisplay.setBlock(this.material.createBlockData());
            blockDisplay.setRotation(BedUtils.yawByBlockFace(this.material != Material.CHEST ? blockFace : blockFace.getOppositeFace()), 0f);
        });
    }

    private @NotNull Location correctLocation(Location location, @NotNull BlockFace blockFace) {
        return switch (blockFace) {
            case NORTH -> location.add(1, 0, 0);
            case SOUTH -> location.add(0, 0, 1);
            case EAST -> location.add(1, 0, 1);
            default -> location;
        };
    }

    // south:xz+1 west:x-1z+1 north:xz-1 east:x+1z-1
}
