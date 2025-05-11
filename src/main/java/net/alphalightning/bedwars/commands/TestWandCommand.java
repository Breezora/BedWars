package net.alphalightning.bedwars.commands;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommand;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommandSource;
import net.alphalightning.bedwars.commands.cloud.sender.PaperPlayerCommandSource;
import net.alphalightning.bedwars.util.SelectionWandTool;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.jetbrains.annotations.NotNull;

public class TestWandCommand extends PaperCommand<@NotNull BedWarsPlugin> {

    public TestWandCommand(BedWarsPlugin plugin) {
        super(plugin);
    }

    @Override
    public void register(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("testwand")
                .senderType(PaperPlayerCommandSource.class)
                .handler(this::runCommand)
        );
    }

    private void runCommand(@NotNull CommandContext<PaperPlayerCommandSource> context) {
        final Player player = (Player) context.sender().plattformSender();
        new SelectionWandTool(plugin, player);
    }
}
