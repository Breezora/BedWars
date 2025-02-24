package net.alphalightning.bedwars.commands.cloud.sender;

import net.alphalightning.bedwars.commands.cloud.AbstractCommand;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PaperCommand<I extends JavaPlugin> extends AbstractCommand<PaperCommandSource, I> {

    public PaperCommand(I plugin) {
        super(plugin);
    }
}
