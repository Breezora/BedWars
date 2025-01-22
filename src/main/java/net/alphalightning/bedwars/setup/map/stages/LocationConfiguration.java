package net.alphalightning.bedwars.setup.map.stages;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface LocationConfiguration {

    default boolean isOnGround(Player player, Location location) {
        return player.isSneaking() && location.subtract(0, 1, 0).getBlock().getType() != Material.AIR;
    }

}
