package net.alphalightning.bedwars.feedback.visual;

import org.jetbrains.annotations.NotNull;

public interface Visualization<T>  extends VisualizationConfiguration {

    void show(@NotNull T t);

}
