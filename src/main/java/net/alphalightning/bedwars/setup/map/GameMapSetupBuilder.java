package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class GameMapSetupBuilder implements MapSetup.Builder {

    @Override
    public MapSetup.Builder plugin(BedWarsPlugin plugin) { return null; }

    @Override
    public MapSetup.Builder executor(Player player) {
        return null;
    }

    @Override
    public MapSetup.Builder name(String name) {
        return null;
    }

    @Override
    public @NotNull MapSetup build() {
        return null;
    }
}
