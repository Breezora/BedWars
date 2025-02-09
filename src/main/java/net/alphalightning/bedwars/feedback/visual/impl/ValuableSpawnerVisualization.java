package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.Visualization;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.game.SpawnerType;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class ValuableSpawnerVisualization implements Visualization<Location> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final Set<Item> spawnedItems = new HashSet<>();
    private final BedWarsPlugin plugin;
    private final MapSetup setup;
    private final SpawnerType type;

    public ValuableSpawnerVisualization(BedWarsPlugin plugin, MapSetup setup, SpawnerType type) {
        this.plugin = plugin;
        this.setup = setup;
        this.type = type;
    }

    @Override
    public void show(@NotNull Location location) {
        final Location spawnLocation = location.clone().add(0, 0.1D, 0);
        final World world = location.getWorld();

        visualize(world, spawnLocation);
    }

    private void visualize(World world, Location spawnLocation) {
        final Item item = spawnItem(world, spawnLocation);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            item.remove();
            this.spawnedItems.remove(item);
        }, 40L);
    }

    private @NotNull Item spawnItem(@NotNull World world, Location spawnLocation) {
        final Location centered = LocationUtil.adjustedCentered(spawnLocation);
        final Item spawnedItem = world.spawn(centered, Item.class, false, item -> {
            item.setItemStack(new ItemStack(this.type == SpawnerType.EMERALD ? Material.EMERALD : Material.DIAMOND));
            item.setCanMobPickup(false);
            item.setCanPlayerPickup(false);
            this.spawnedItems.add(item);
        });
        this.visualizationManager.trackEntity(this.setup, spawnedItem);
        return spawnedItem;
    }
}
