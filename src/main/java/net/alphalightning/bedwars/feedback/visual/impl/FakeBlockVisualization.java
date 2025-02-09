package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.feedback.visual.Visualization;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.util.BedUtil;
import net.alphalightning.bedwars.util.BlockUtil;
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

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final MapSetup setup;
    private final Player player;
    private final Material material;

    public FakeBlockVisualization(MapSetup setup, Player player, Material material) {
        this.setup = setup;
        this.player = player;
        this.material = material;
    }

    @Override
    public void show(@NotNull Location location) {
        final World world = location.getWorld();
        final BlockFace blockFace = BlockUtil.cardinalDirection(this.player);

        if (material.name().contains("BED")) {
            location = correctLocation(location, blockFace, true);
        } else {
            location = correctLocation(location, blockFace, false);
        }

        this.visualizationManager.trackEntity(this.setup, world.spawnEntity(location.toBlockLocation(), EntityType.BLOCK_DISPLAY, SpawnReason.CUSTOM, entity -> {
            final BlockDisplay blockDisplay = (BlockDisplay) entity;
            blockDisplay.setBlock(this.material.createBlockData());
            blockDisplay.setRotation(BedUtil.yawByBlockFace(this.material != Material.CHEST ? blockFace : blockFace.getOppositeFace()), 0f);
        }));
    }

    private @NotNull Location correctLocation(Location location, @NotNull BlockFace blockFace, boolean isBed) {
        if (isBed) {
            return switch (blockFace) {
                case NORTH -> location.add(1, 0, 0);
                case SOUTH -> location.add(0, 0, 1);
                case EAST -> location.add(1, 0, 1);
                default -> location;
            };
        }
        return switch (blockFace) {
            case SOUTH -> location.add(1, 0, 1);
            case WEST -> location.add(0, 0, 1);
            case EAST -> location.add(1, 0, 0);
            default -> location;
        };
    }
}
