package net.alphalightning.bedwars.commands.cloud.sender;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;

public class PaperPlayerCommandSource extends PaperCommandSource {

    public PaperPlayerCommandSource(Player plattformSender, CommandSourceStack commandSourceStack) {
        super(plattformSender, commandSourceStack);
    }
}
