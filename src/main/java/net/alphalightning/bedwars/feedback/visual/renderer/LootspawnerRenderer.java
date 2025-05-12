package net.alphalightning.bedwars.feedback.visual.renderer;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.BaseRenderer;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class LootspawnerRenderer extends BaseRenderer implements VisualizationRenderer<LootspawnerVisualization> {

    private final Location location;
    private BukkitTask currentTask;

    public LootspawnerRenderer(BedWarsPlugin plugin, MapSetup setup, Location location) {
        super(plugin, setup);
        this.location = location;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull LootspawnerVisualization visualisation) {
        System.out.println("=== LootspawnerRenderer.render() ===");
        System.out.println("Current Task: " + (currentTask != null ? currentTask.getTaskId() : "null"));

        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel();
            super.visualizationManager.removeLastTask(this.setup);
        }

        this.currentTask = Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                () -> visualisation.show(this.location),
                0L,
                10L
        );

        System.out.println("New Task ID: " + currentTask.getTaskId());
        return super.visualizationManager.registerTask(this.setup, currentTask);
    }
}
