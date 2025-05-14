package net.alphalightning.bedwars.feedback.visual.renderer;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.BaseRenderer;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class BoundingBoxRenderer<T> extends BaseRenderer {

    private BukkitTask currentTask;

    public BoundingBoxRenderer(BedWarsPlugin plugin, MapSetup setup) {
        super(plugin, setup);
    }

    public @NotNull BukkitTask render(@NotNull T visualization, int color) {
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel();
            super.visualizationManager.removeLastTask(this.setup);
        }

        this.currentTask = Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                () -> new BoundingBoxVisualization<>(color).show(visualization),
                0L,
                10L
        );
        return super.visualizationManager.registerTask(this.setup, currentTask);
    }
}
