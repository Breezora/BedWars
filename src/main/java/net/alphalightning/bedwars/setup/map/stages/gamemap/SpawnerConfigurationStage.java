package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.ui.GameMapConfigurationOverviewGui;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class SpawnerConfigurationStage extends Stage {

    public SpawnerConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }
        new GameMapConfigurationOverviewGui(player, gameMapSetup).showGui();
        player.sendMessage(Component.translatable("mapsetup.stage.2"));
    }
}
