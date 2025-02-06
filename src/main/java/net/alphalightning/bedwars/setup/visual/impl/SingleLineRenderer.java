package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.visual.VisualisationRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class SingleLineRenderer implements VisualisationRenderer<SingleLineVisualisation> {

    private final BedWarsPlugin plugin;

    public SingleLineRenderer(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull SingleLineVisualisation visualisation) {
        final Location start = visualisation.player().getEyeLocation();
        return Bukkit.getScheduler().runTaskTimer(plugin, () -> visualisation.show(start), 0L, 5L);
    }
}
