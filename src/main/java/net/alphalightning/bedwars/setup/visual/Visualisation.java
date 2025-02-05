package net.alphalightning.bedwars.setup.visual;

import org.jetbrains.annotations.NotNull;

public interface Visualisation<T>  extends VisualisationConfiguration {

    void show(@NotNull T t);

}
