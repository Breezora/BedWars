package net.alphalightning.bedwars.setup.visual;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public interface VisualisationRenderer<T extends Visualisation<?>> {

    @NotNull BukkitTask render(@NotNull T visualisation);

}
