package net.alphalightning.bedwars.feedback.visual;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public interface VisualizationRenderer<T extends Visualization<?>> {

    @NotNull BukkitTask render(@NotNull T visualisation);

}
