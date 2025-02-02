package net.alphalightning.bedwars.provider.impl;

import net.alphalightning.bedwars.provider.TextProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MapNameProvider implements TextProvider {

    private final Supplier<Stream<String>> textSupplier;

    public MapNameProvider(Supplier<Stream<String>> textSupplier) {
        this.textSupplier = textSupplier;
    }

    @Override
    public @NotNull Collection<String> texts() {
        return this.textSupplier.get().toList();
    }

    @Override
    public int count() {
        return (int) this.textSupplier.get().count();
    }
}
