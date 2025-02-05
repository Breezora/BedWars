package net.alphalightning.bedwars.setup.visual;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

public interface Visualizer<T> {

    void visualize(@NotNull T t);

    void spawnVisual(@NotNull Location location);

    void renderDisplay(@NotNull Display display);

}
