package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.Visualization;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class SpawnerVisualization implements Visualization<Location> {

    private final BedWarsPlugin plugin;

    private final Set<Item> spawnedItems = new HashSet<>();
    private int counter;

    public SpawnerVisualization(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void show(@NotNull Location location) {
        final Location spawnLocation = location.clone().add(0, 0.1D, 0);
        final World world = location.getWorld();

        if (world == null) {
            return;
        }

        visualizeIron(world, spawnLocation);

        if (this.counter % 10 == 0) { // Every 5s (10 * 0.5s = 5s)
            visualizeGold(world, spawnLocation);
        }
        this.counter++;
    }

    private void visualizeIron(@NotNull World world, Location spawnLocation) {
        final Item iron = spawnItem(world, spawnLocation, Material.IRON_INGOT);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            iron.remove();
            this.spawnedItems.remove(iron);
        }, 40L);
    }

    private void visualizeGold(World world, Location spawnLocation) {
        final Item gold = spawnItem(world, spawnLocation, Material.GOLD_INGOT);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            gold.remove();
            this.spawnedItems.remove(gold);
        }, 40L);
    }

    private @NotNull Item spawnItem(@NotNull World world, @NotNull Location spawnLocation, Material material) {
        final Location centered = spawnLocation.clone();
        centered.setX(spawnLocation.getBlockX() + 0.5D);
        centered.setZ(spawnLocation.getBlockZ() + 0.5D);

        if (centered.getY() % 1 == 0.5) { // Is block a half slab
            centered.setY(0.6D);
        } else {
            centered.setY(centered.y() + 0.1D);
        }

        return world.dropItemNaturally(centered.add(0, 0, 0), new ItemStack(material), item -> { // #add(0, 0, 0) is a bypass to spawn at the exact location and avoid randomness
            item.setItemStack(new ItemStack(material));
            item.setCanMobPickup(false);
            item.setCanPlayerPickup(false);
            this.spawnedItems.add(item);
        });
    }
}
