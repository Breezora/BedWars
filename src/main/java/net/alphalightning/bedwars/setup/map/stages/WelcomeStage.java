package net.alphalightning.bedwars.setup.map.stages;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class WelcomeStage extends Stage {

    private final boolean isLobbySetup;

    public WelcomeStage(BedWarsPlugin plugin, Player player, MapSetup setup, boolean isLobbySetup) {
        super(plugin, player, setup);
        this.isLobbySetup = isLobbySetup;
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable(isLobbySetup ? "lobbysetup.start" : "mapsetup.start"));
        Feedback.start(player);
    }
}
