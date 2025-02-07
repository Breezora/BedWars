package net.alphalightning.bedwars.feedback.visual;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.impl.MultiBlockRenderer;
import net.alphalightning.bedwars.feedback.visual.impl.MultiBlockVisualization;
import net.alphalightning.bedwars.feedback.visual.impl.SingleLineRenderer;
import net.alphalightning.bedwars.feedback.visual.impl.SingleLineVisualization;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UnboundTeamVisuals {

    static void renderSpawnpoint(BedWarsPlugin plugin, Player player, @NotNull Location withOffset) {
        new MultiBlockRenderer(plugin, List.of(withOffset.getBlock(), withOffset.add(0, 1, 0).getBlock()))
                .render(new MultiBlockVisualization(0xecb8f5));
        new SingleLineRenderer(plugin, player).render(new SingleLineVisualization(player));
    }

}
