package net.alphalightning.bedwars.feedback.visual.renderer;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.BaseRenderer;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
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
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel();
            super.visualizationManager.removeLastTask(this.setup);
        }

        this.currentTask = Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                () -> visualisation.show(player.getEyeLocation()),
                0L,
                10L
        );
        return super.visualizationManager.registerTask(this.setup, currentTask);
    }
}
