package net.alphalightning.bedwars.feedback.visual;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.impl.*;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UnboundTeamVisuals {

    VisualizationManager MANAGER = VisualizationManager.instance();

    static void renderSpawnpoint(BedWarsPlugin plugin, MapSetup setup, Player player, @NotNull Location withOffset) {
        final List<Block> blocks = List.of(withOffset.getBlock(), withOffset.add(0, 1, 0).getBlock());

        MANAGER.registerTask(setup, new BoundingBoxRenderer<List<Block>>(plugin, setup).render(blocks, 0xecb8f5));
        MANAGER.registerTask(setup, new SingleLineRenderer(plugin, setup, player).render(new SingleLineVisualization(player)));
    }

    static void renderShop(BedWarsPlugin plugin, MapSetup setup, Player player, @NotNull Location location, @NotNull Location withOffset, Component name) {
        MANAGER.registerTask(setup, new EntityRenderer(plugin, setup, location.toCenterLocation().subtract(0, 0.5D, 0)).render(new EntityVisualization(setup, player, EntityType.VILLAGER, null, null)));
        MANAGER.registerTask(setup, new EntityRenderer(plugin, setup, withOffset.toCenterLocation().add(0, 1.7D, 0)).render(new EntityVisualization(setup, player, EntityType.TEXT_DISPLAY, null, Component.translatable("entity.interact"))));
        MANAGER.registerTask(setup, new EntityRenderer(plugin, setup, withOffset.toCenterLocation().add(0, 1.95D, 0)).render(new EntityVisualization(setup, player, EntityType.TEXT_DISPLAY, null, name)));
        MANAGER.registerTask(setup, Bukkit.getScheduler().runTaskLater(plugin, () -> UnboundTeamVisuals.renderSpawnpoint(plugin, setup, player, withOffset), 1L)); // Render this later to avoid some stupid collisions with the texts and villager
    }

}
