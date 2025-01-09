package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.setup.Setup;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public sealed interface MapSetup extends Setup permits LobbyMapSetup, GameMapSetup {

    sealed interface Builder extends Setup.Builder<Builder> permits LobbyMapSetupBuilder, GameMapSetupBuilder {

        @Override
        Builder executor(Player player);

        @Override
        Builder name(String name);

        @NotNull MapSetup build();
    }
}
