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
        final Location topHalf = BedUtils.calculateHeadLocation(location, blockFace);

        if (topHalf == null) {
            throw new IllegalStateException("Top half of bed could not be calculated");
        }
        world.spawnEntity(location, EntityType.BLOCK_DISPLAY, SpawnReason.CUSTOM, entity -> {
            final BlockDisplay blockDisplay = (BlockDisplay) entity;
            blockDisplay.setBlock(this.material.createBlockData());
            blockDisplay.setRotation(BedUtils.yawByBlockFace(blockFace), 0f);
        });
    }
}
