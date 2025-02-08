package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.VisualizationRenderer;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class TextRenderer implements VisualizationRenderer<TextVisualization> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final BedWarsPlugin plugin;
    private final MapSetup setup;
    private final Location location;

    public TextRenderer(BedWarsPlugin plugin, MapSetup setup, Location location) {
        this.plugin = plugin;
        this.setup = setup;
        this.location = location;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull TextVisualization visualisation) {
        return this.visualizationManager.registerTask(this.setup, Bukkit.getScheduler().runTask(this.plugin, () -> {
            final Location withoutHeadRotation = this.location.clone();
            withoutHeadRotation.setPitch(0f);

            visualisation.show(withoutHeadRotation);
        }));
    }
}
