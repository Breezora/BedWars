package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class LobbyMapSetupBuilder implements MapSetup.Builder {

    private BedWarsPlugin plugin;
    private Player player;

    @Override
    public MapSetup.Builder plugin(BedWarsPlugin plugin) {
        this.plugin = plugin;
        return this;
    }

    @Override
    public MapSetup.Builder executor(Player player) {
        this.player = player;
        return this;
    }

    @Override
    public MapSetup.Builder name(String name) {
        throw new UnsupportedOperationException("A lobby cannot have a more specific name");
    }

    @Override
    public @NotNull MapSetup build() {
        if (plugin == null) {
            throw new IllegalStateException("The plugin cannot be null");
        }
        if (player == null) {
            throw new IllegalStateException("The player cannot be null");
        }
        return new LobbyMapSetup(plugin, player);
    }
}
