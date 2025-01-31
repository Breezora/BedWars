package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DebugStage extends Stage {

    private final GameMapSetup setup;

    public DebugStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.setup = null;
            return;
        }
        this.setup = gameMapSetup;
    }

    @Override
    public void run() {
        Bukkit.getLogger().info(setup.toString());
    }
}
