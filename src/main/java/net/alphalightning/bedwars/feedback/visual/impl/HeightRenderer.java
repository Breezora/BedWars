package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.BaseRenderer;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class HeightRenderer extends BaseRenderer implements VisualizationRenderer<HeightVisualization> {

    private final Player player;

    public HeightRenderer(BedWarsPlugin plugin, MapSetup setup, Player player) {
        super(plugin, setup);
        this.player = player;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull HeightVisualization visualisation) {
        return super.visualizationManager.registerTask(this.setup, Bukkit.getScheduler().runTaskTimer(plugin, () -> visualisation.show(player.getLocation()), 0L, 5L));
    }
}
