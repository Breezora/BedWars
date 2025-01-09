package net.alphalightning.bedwars.setup.map;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class LobbyMapSetupBuilder implements MapSetup.Builder {

    private Player player;

    // Start debug
    private int stage;

    public void stage(int stage) {
        this.stage = stage;
    }

    // End debug

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
        return new LobbyMapSetup(player, stage);
    }
}
