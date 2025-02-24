package net.alphalightning.bedwars.commands.cloud.sender;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;

public class PaperCommandSource extends CommandSource<CommandSender> {

    private final CommandSourceStack commandSourceStack;

    public PaperCommandSource(CommandSender plattformSender, CommandSourceStack commandSourceStack) {
        super(plattformSender);
        this.commandSourceStack = commandSourceStack;
    }

    public CommandSourceStack commandSourceStack() {
        return commandSourceStack;
    }

}
