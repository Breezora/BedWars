package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class GameMapSetupBuilder implements MapSetup.Builder {

    private BedWarsPlugin plugin;
    private Player player;
    private String name;

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
        this.name = name;
        return this;
    }

    @Override
    public @NotNull MapSetup build() {
        if (plugin == null) {
            throw new IllegalStateException("The plugin cannot be null");
        }
        if (player == null) {
            throw new IllegalStateException("The player cannot be null");
        }
        if (name == null) {
            throw new IllegalStateException("The name cannot be null");
        }
        return new GameMapSetup(plugin, player, name);
    }
}
