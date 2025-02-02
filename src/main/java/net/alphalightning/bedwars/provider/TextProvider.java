package net.alphalightning.bedwars.provider;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface TextProvider {

    @NotNull Collection<String> texts();

    int count();

}
