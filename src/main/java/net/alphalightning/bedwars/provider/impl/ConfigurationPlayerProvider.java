package net.alphalightning.bedwars.provider.impl;

import net.alphalightning.bedwars.provider.PlayerProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ConfigurationPlayerProvider implements PlayerProvider {

    private final Supplier<Stream<Player>> playerSupplier;

    public ConfigurationPlayerProvider(Supplier<Stream<Player>> playerSupplier) {
        this.playerSupplier = playerSupplier;
    }

    @Override
    public @NotNull Collection<Player> players() {
        return this.playerSupplier.get().toList();
    }

    @Override
    public int count() {
        return (int) this.playerSupplier.get().count();
    }
}
