package net.alphalightning.bedwars.setup.visual;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface Visualizer<T> {

    void visualize(@NotNull T t);

    void spawnVisual(@NotNull Location location);

}
