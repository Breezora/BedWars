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

    public SingleLineRenderer(BedWarsPlugin plugin, MapSetup setup, Player player) {
        super(plugin, setup);
        this.player = player;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull SingleLineVisualization visualisation) {
        final Location start = this.player.getEyeLocation();
        return super.visualizationManager.registerTask(
                this.setup,
                Bukkit.getScheduler().runTaskTimer(plugin, () -> visualisation.show(start), 0L, 5L)
        );
    }
}
