package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.entity.Player;

public final class LobbyMapSetup implements MapSetup {

    private final ComponentLogger logger;

    private final Player player;
    private int stage;

    public LobbyMapSetup(BedWarsPlugin plugin, Player player) {
        this.logger = plugin.getComponentLogger();
        this.player = player;
        startStage(0);
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
        if (this.stage > stage) {
            throw new IllegalStateException("Could not start stage " + stage + ": The new stage must be at least the current stage (" + this.stage + ")or higher.");
        }
        this.stage = stage;

        switch (stage) {
            case 0 -> player.sendMessage(Component.translatable("lobbysetup.start"));
            case 1 -> player.sendMessage(Component.translatable("lobbysetup.stage1"));
            case 2 -> player.sendMessage(Component.translatable("lobbysetup.stage2"));
            case 3 -> player.sendMessage(Component.translatable("lobbysetup.finish"));
            default -> logger.warn(Component.translatable("lobbysetup.cancel"));
        }
    }
}
