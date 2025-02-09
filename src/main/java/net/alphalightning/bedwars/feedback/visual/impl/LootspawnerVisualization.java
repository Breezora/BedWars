package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.Visualization;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.game.SpawnerType;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
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

public class LootspawnerVisualization implements Visualization<Location> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final Set<Item> spawnedItems = new HashSet<>();
    private final BedWarsPlugin plugin;
    private final MapSetup setup;
    private final SpawnerType spawnerType;
    private final boolean isTeam;
    private int counter;

    public LootspawnerVisualization(BedWarsPlugin plugin, MapSetup setup, SpawnerType spawnerType, boolean isTeam) {
        this.plugin = plugin;
        this.setup = setup;
        this.spawnerType = spawnerType;
        this.isTeam = isTeam;
    }

    @Override
    public void show(@NotNull Location location) {
        final Location spawnLocation = location.clone().add(0, 0.1D, 0);
        final World world = location.getWorld();

        if (world == null) {
            return;
        }

        if (!isTeam) {
            spawnItem(world, spawnLocation, this.spawnerType == SpawnerType.EMERALD ? Material.EMERALD : Material.DIAMOND);

        } else {
            if (!(this.setup instanceof GameMapSetup gameMapSetup)) {
                return;
            }
            if (!gameMapSetup.hasSlowIron()) {
                spawnItem(world, spawnLocation, Material.IRON_INGOT);
            }
            spawnItem(world, spawnLocation, Material.IRON_INGOT);

            if (this.counter % 10 == 0) {
                spawnItem(world, spawnLocation, Material.GOLD_INGOT);
            }
        }
        this.counter++;

    }

    private void spawnItem(@NotNull World world, Location spawnLocation, Material material) {
        final Location centered = LocationUtil.adjustedCentered(spawnLocation);
        final Item spawnedItem = world.spawn(centered, Item.class, false, item -> {
            item.setItemStack(new ItemStack(material));
            item.setCanMobPickup(false);
            item.setCanPlayerPickup(false);
            this.spawnedItems.add(item);
        });
        this.visualizationManager.trackEntity(this.setup, spawnedItem);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            this.spawnedItems.remove(spawnedItem);
            spawnedItem.remove();
        }, 40L);
    }
}
