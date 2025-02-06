package net.alphalightning.bedwars.setup.visual;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.visual.impl.MultiBlockRenderer;
import net.alphalightning.bedwars.setup.visual.impl.MultiBlockVisualisation;
import net.alphalightning.bedwars.setup.visual.impl.SingleLineRenderer;
import net.alphalightning.bedwars.setup.visual.impl.SingleLineVisualisation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UnboundTeamVisuals {

    static void renderSpawnpoint(BedWarsPlugin plugin, Player player, @NotNull Location withOffset) {
        new MultiBlockRenderer(plugin, List.of(withOffset.getBlock(), withOffset.add(0, 1, 0).getBlock()))
                .render(new MultiBlockVisualisation(0xecb8f5));
        new SingleLineRenderer(plugin, player).render(new SingleLineVisualisation(player));
    }

}
