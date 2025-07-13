package net.alphalightning.bedwars.game.entity;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.jackson.JacksonLocation;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;

public class VillagerManager {

    private final BedWarsPlugin plugin;

    public VillagerManager(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    public void createVillagers(JacksonLocation location, VillagerType villagerType) {
        Location villagerLocation = location.asBukkitLocation();

        if (villagerLocation == null) return;

        Villager shopVillager = villagerLocation.getWorld().spawn(villagerLocation.toCenterLocation().subtract(0, 0.5D, 0), Villager.class);
        shopVillager.setAI(false);
        shopVillager.setSilent(true);
        shopVillager.setGravity(true); // Add gravity to avoid wrong spawning (floating). Temporary fix
        shopVillager.setNoPhysics(true);
        shopVillager.setInvulnerable(true);
        TextDisplay textDisplayTop = villagerLocation.getWorld().spawn(villagerLocation.toCenterLocation().add(0, 1.95D, 0), TextDisplay.class);
        TextDisplay textDisplayBottom = villagerLocation.getWorld().spawn(villagerLocation.toCenterLocation().add(0, 1.7D, 0), TextDisplay.class);

        String key = switch (villagerType) {
            case SHOP -> "entity.villager.shop.item";
            case UPGRADE -> "entity.villager.shop.upgrade";
        };

        shopVillager.setMetadata("type", new FixedMetadataValue(plugin, villagerType));

        textDisplayTop.text(Component.translatable(key));
        textDisplayBottom.text(Component.translatable("entity.interact"));

    }
}
