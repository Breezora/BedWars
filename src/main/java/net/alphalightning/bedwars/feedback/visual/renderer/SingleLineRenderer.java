package net.alphalightning.bedwars.feedback.visual.renderer;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.BaseRenderer;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class SingleLineRenderer extends BaseRenderer implements VisualizationRenderer<SingleLineVisualization> {

    private final Player player;
    private BukkitTask currentTask;

    public SingleLineRenderer(BedWarsPlugin plugin, MapSetup setup, Player player) {
        super(plugin, setup);
        this.player = player;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull SingleLineVisualization visualisation) {
        Location location = player.getEyeLocation();

        System.out.println("=== SingleLineRenderer.render() ===");
        System.out.println("Current Task: " + (currentTask != null ? currentTask.getTaskId() : "null"));

        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel();
            super.visualizationManager.removeLastTask(this.setup);
        }

        this.currentTask = Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                () -> visualisation.show(location),
                0L,
                10L
        );

        System.out.println("New Task ID: " + currentTask.getTaskId());
        return super.visualizationManager.registerTask(this.setup, currentTask);
    }
}
