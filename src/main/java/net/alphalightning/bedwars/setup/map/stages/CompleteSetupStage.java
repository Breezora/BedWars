package net.alphalightning.bedwars.setup.map.stages;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class CompleteSetupStage extends Stage {

    private final String fileName;
    private final String binaryFileName;
    private final boolean isLobbySetup;

    public CompleteSetupStage(BedWarsPlugin plugin, Player player, MapSetup setup, String fileName, String binaryFileName, boolean isLobbySetup) {
        super(plugin, player, setup);
        this.fileName = fileName;
        this.binaryFileName = binaryFileName;
        this.isLobbySetup = isLobbySetup;
    }

    @Override
    public void run() {
        if (isLobbySetup) {
            player.sendMessage(Component.translatable("lobbysetup.finish", Component.text(fileName)));
        } else {
            player.sendMessage(Component.translatable("mapsetup.finish", Component.text(fileName), Component.text(binaryFileName)));
        }
        Feedback.complete(player);
    }
}
