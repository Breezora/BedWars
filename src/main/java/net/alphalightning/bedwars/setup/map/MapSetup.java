package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.Setup;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public sealed interface MapSetup extends Setup permits LobbyMapSetup, GameMapSetup {

    default int validateStage(int current, int toStart) {
        if (current > toStart) {
            throw new IllegalStateException("Could not start stage " + toStart + ": The new stage must be at least the current stage (" + current + ") or higher.");
        }
        return toStart;
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
