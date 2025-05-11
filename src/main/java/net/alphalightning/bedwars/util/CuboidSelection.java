package net.alphalightning.bedwars.util;

import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public record CuboidSelection(JacksonTeam owner, Location first, Location second) {

    public @NotNull @Unmodifiable List<Block> corners() {
        return List.of(first.getBlock(), second.getBlock());
    }


}
