package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.ui.GameMapConfigurationOverviewGui;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class TeamSelectionStage extends Stage {

    public TeamSelectionStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        new GameMapConfigurationOverviewGui(player, (GameMapSetup) setup).showGui();
        player.sendMessage(Component.translatable("mapsetup.stage.1"));
    }
}
