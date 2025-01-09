package net.alphalightning.bedwars.setup.map;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class LobbyMapSetup implements MapSetup {

    private static final String FILE_NAME = "lobby.json";

    private final Player player;
    private int stage;

    public LobbyMapSetup(Player player) {
        this.player = player;
    }

    @Override
    public void start() {
        startStage(1);
    }

    @Override
    public void finish() {

    }

    @Override
    public void saveConfiguration() {

    }

    private void startStage(int stage) {
        if (this.stage >= stage) {
            throw new IllegalStateException("Could not start stage " + stage + ": The new stage must be higher than the current stage (" + this.stage + ")");
        }
        this.stage = stage;

        switch (stage) {
            case 1 -> player.sendMessage(Component.translatable("lobbysetup.start"));
            case 2 -> player.sendMessage(Component.translatable("lobbysetup.finish", Component.text(FILE_NAME)));
        }
    }
}
