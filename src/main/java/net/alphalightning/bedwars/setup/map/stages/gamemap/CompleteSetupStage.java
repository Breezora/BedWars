package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class CompleteSetupStage extends Stage {

    private final String fileName;

    public CompleteSetupStage(BedWarsPlugin plugin, Player player, MapSetup setup, String fileName) {
        super(plugin, player, setup);
        this.fileName = fileName;
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.finish", Component.text(fileName)));
        Feedback.complete(player);
    }
}
