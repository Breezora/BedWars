package net.alphalightning.bedwars.feedback.visual.manager;

import net.alphalightning.bedwars.provider.ServiceProvider;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VisualizationManager implements ServiceProvider<BukkitTask> {

    private static VisualizationManager instance;
    private final Map<MapSetup, List<BukkitTask>> activeRenderings;

    private VisualizationManager() {
        this.activeRenderings = new ConcurrentHashMap<>();
    }

    public static synchronized VisualizationManager instance() {
        if (instance == null) {
            instance = new VisualizationManager();
        }
        return instance;
    }

    public synchronized BukkitTask registerTask(@NotNull MapSetup setup, @NotNull BukkitTask task) {
        final List<BukkitTask> tasks = this.activeRenderings.getOrDefault(setup, new ArrayList<>());
        tasks.add(task);

        this.activeRenderings.put(setup, tasks);
        return task;
    }

    public synchronized void unregisterAll(@NotNull MapSetup setup) {
        this.activeRenderings.getOrDefault(setup, new ArrayList<>()).forEach(BukkitTask::cancel);
        this.activeRenderings.remove(setup);
    }

    @Override
    public @NotNull BukkitTask get() {
        throw new UnsupportedOperationException();
    }
}
