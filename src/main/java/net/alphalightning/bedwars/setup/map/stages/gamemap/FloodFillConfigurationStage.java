package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.map.stages.TeamConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FloodFillConfigurationStage extends Stage implements TeamConfiguration {

    public FloodFillConfigurationStage(@NotNull BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
    }
}
