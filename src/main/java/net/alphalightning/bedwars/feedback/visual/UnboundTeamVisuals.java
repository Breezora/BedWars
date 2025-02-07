package net.alphalightning.bedwars.feedback.visual;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.impl.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UnboundTeamVisuals {

    static void renderSpawnpoint(BedWarsPlugin plugin, Player player, @NotNull Location withOffset) {
        new MultiBlockRenderer(plugin, List.of(withOffset.getBlock(), withOffset.add(0, 1, 0).getBlock()))
                .render(new MultiBlockVisualization(0xecb8f5));
        new SingleLineRenderer(plugin, player).render(new SingleLineVisualization(player));
    }

    static void renderShop(BedWarsPlugin plugin, Player player, @NotNull Location location, @NotNull Location withOffset, Component name) {
        new EntityRenderer(plugin, location.toCenterLocation().subtract(0, 0.5D, 0)).render(new EntityVisualization(EntityType.VILLAGER, null));
        new TextRenderer(plugin, withOffset.toCenterLocation().add(0, 1.7D, 0)).render(new TextVisualization(Component.translatable("entity.interact")));
        new TextRenderer(plugin, withOffset.toCenterLocation().add(0, 1.95D, 0)).render(new TextVisualization(name));
        Bukkit.getScheduler().runTaskLater(plugin, () -> UnboundTeamVisuals.renderSpawnpoint(plugin, player, withOffset), 1L); // Render this later to avoid some stupid collisions with the texts and villager

    }

}
