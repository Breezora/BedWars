package net.alphalightning.bedwars.feedback.visual;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;

public abstract class BaseRenderer {

    protected final VisualizationManager visualizationManager = VisualizationManager.instance();
    protected final BedWarsPlugin plugin;
    protected final MapSetup setup;

    public BaseRenderer(BedWarsPlugin plugin, MapSetup setup) {
        this.plugin = plugin;
        this.setup = setup;
    }
}
