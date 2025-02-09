package net.alphalightning.bedwars.feedback.visual;

import org.jetbrains.annotations.NotNull;

public interface Visualization<T> {

    double STEP = 0.15D;
    float SIZE = 0.75F;

    void show(@NotNull T t);

}
