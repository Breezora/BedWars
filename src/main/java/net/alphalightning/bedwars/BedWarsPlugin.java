package net.alphalightning.bedwars;

import co.aikar.commands.PaperCommandManager;
import net.alphalightning.bedwars.commands.SetSpawnPointCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class BedWarsPlugin extends JavaPlugin {

    public void onEnable() {
        registerCommands();
    }

    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(new BedWarsPlugin());

        manager.registerCommand(new SetSpawnPointCommand());
    }
}
