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
        System.out.println("=== BoundingBoxRenderer.render() ===");
        System.out.println("Current Task: " + (currentTask != null ? currentTask.getTaskId() : "null"));

        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel();
            super.visualizationManager.removeLastTask(this.setup);
        }

        BoundingBoxVisualization<T> boxVisualization = new BoundingBoxVisualization<>(color);

        // Task erstellen aber NICHT registrieren
        this.currentTask = Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                () -> boxVisualization.show(visualization),
                0L,
                10L
        );

        System.out.println("New Task ID: " + currentTask.getTaskId());

        // Nur EINMAL registrieren
        return super.visualizationManager.registerTask(this.setup, currentTask);
    }
}
