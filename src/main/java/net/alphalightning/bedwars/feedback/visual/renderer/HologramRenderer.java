package net.alphalightning.bedwars.feedback.visual.renderer;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.BaseRenderer;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class HologramRenderer extends BaseRenderer implements VisualizationRenderer<HologramVisualization> {

    private final Location location;

    public HologramRenderer(BedWarsPlugin plugin, MapSetup setup, Location location) {
        super(plugin, setup);
        this.location = location;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull HologramVisualization visualisation) {
        return super.visualizationManager.registerTask(
                this.setup,
                Bukkit.getScheduler().runTask(this.plugin, () -> visualisation.show(this.location))
        );
    }
}
