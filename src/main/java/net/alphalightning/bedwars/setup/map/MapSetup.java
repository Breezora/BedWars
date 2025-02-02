package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.Setup;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public sealed interface MapSetup extends Setup permits LobbyMapSetup, GameMapSetup {

    @NotNull String mapName();

    void invalidate();

    void startStage(int stage);

    default int validateStage(int current, int toStart) {
        if (current > toStart) {
            throw new IllegalStateException("Could not start stage " + toStart + ": The new stage must be at least the current stage (" + current + ") or higher.");
        }
        return toStart;
    }

    @Override
    default void start() {
        startStage(1);
    }

    @Override
    default void finish(int lastStage) {
        startStage(lastStage);
        saveConfiguration();
    }

    @Override
    default void cancel() {
        startStage(100);
        invalidate();
    }

    sealed interface Builder extends Setup.Builder<Builder> permits LobbyMapSetupBuilder, GameMapSetupBuilder {

        @Override
        Builder plugin(BedWarsPlugin plugin);

        @Override
        Builder executor(Player player);

        @Override
        Builder name(String name);

        @NotNull MapSetup build();
    }
}
