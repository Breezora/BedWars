package net.alphalightning.bedwars.game.entity;

import net.alphalightning.bedwars.setup.map.jackson.JacksonLocation;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.Villager;

public class VillagerManager {

    public void createVillagers(JacksonLocation location, VillagerType villagerType) {
        Location villagerLocation = location.asBukkitLocation();

        if (villagerLocation == null) return;

        Villager shopVillager = villagerLocation.getWorld().spawn(villagerLocation.toCenterLocation().subtract(0, 0.5D, 0), Villager.class);
        shopVillager.setAI(false);
        shopVillager.setSilent(true);
        shopVillager.setGravity(false);
        shopVillager.setNoPhysics(true);
        shopVillager.setInvulnerable(true);

        TextDisplay textDisplayTop = villagerLocation.getWorld().spawn(villagerLocation.toCenterLocation().add(0, 1.95D, 0), TextDisplay.class);
        TextDisplay textDisplayBottom = villagerLocation.getWorld().spawn(villagerLocation.toCenterLocation().add(0, 1.7D, 0), TextDisplay.class);

        String key = switch (villagerType) {
            case SHOP -> "entity.villager.shop.item";
            case UPGRADE -> "entity.villager.shop.upgrade";
        };

        textDisplayTop.text(Component.translatable(key));
        textDisplayBottom.text(Component.translatable("entity.interact"));

    }
}
