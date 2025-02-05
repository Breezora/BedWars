package net.alphalightning.bedwars.provider;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface ServiceProvider<T> {

    @NotNull T get();

    static <T> @NotNull ServiceProvider<T> of(@NotNull Supplier<T> supplier) {
        return supplier::get;
    }

}
