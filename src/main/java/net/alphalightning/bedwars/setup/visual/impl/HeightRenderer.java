package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.visual.VisualizationRenderer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class HeightRenderer implements VisualizationRenderer<HeightVisualization> {

    private final BedWarsPlugin plugin;
    private final Player player;

    public HeightRenderer(BedWarsPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull HeightVisualization visualisation) {
        return Bukkit.getScheduler().runTaskTimer(plugin, () -> visualisation.show(player.getLocation()), 0L, 5L);
    }
}
