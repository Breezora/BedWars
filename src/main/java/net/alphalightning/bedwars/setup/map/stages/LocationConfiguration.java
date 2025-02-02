package net.alphalightning.bedwars.setup.map.stages;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public interface LocationConfiguration {

    Vector OFFSET = new Vector(0, 1, 0); // Used to correct values in sneak configurations

    default boolean isNotOnGround(Player player, Location location) {
        return !player.isSneaking() || location.subtract(0, 1, 0).getBlock().getType() == Material.AIR;
    }

}
