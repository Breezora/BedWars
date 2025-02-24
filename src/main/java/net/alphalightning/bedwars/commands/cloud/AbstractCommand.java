package net.alphalightning.bedwars.commands.cloud;

import org.incendo.cloud.CommandManager;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand<C, I> {

    protected final I plugin;

    public AbstractCommand(I plugin) {
        this.plugin = plugin;
    }

    public abstract void register(@NotNull CommandManager<C> commandManager);
}
