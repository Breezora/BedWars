package net.alphalightning.bedwars.feedback.visual.renderer;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.BaseRenderer;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class BoundingBoxRenderer<T> extends BaseRenderer {

    public BoundingBoxRenderer(BedWarsPlugin plugin, MapSetup setup) {
        super(plugin, setup);
    }

    public @NotNull BukkitTask render(@NotNull T visualization, int color) {
        return super.visualizationManager.registerTask(
                this.setup,
                Bukkit.getScheduler().runTaskTimer(this.plugin, () -> new BoundingBoxVisualization<T>(color).show(visualization), 0L, 5L)
        );
    }

}
