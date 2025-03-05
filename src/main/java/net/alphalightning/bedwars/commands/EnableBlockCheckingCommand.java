package net.alphalightning.bedwars.commands;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommand;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommandSource;
import net.alphalightning.bedwars.commands.cloud.sender.PaperPlayerCommandSource;
import net.alphalightning.bedwars.game.listener.BlockListener;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.description.CommandDescription;
import org.jetbrains.annotations.NotNull;

public class EnableBlockCheckingCommand extends PaperCommand<@NotNull BedWarsPlugin> {

    public EnableBlockCheckingCommand(@NotNull BedWarsPlugin plugin) { super(plugin); }

    @Override
    public void register(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("enableblockchecking")
                .commandDescription(CommandDescription.commandDescription("Zum aktivieren und deaktivieren vom Block check"))
                .senderType(PaperPlayerCommandSource.class)
                .permission("bedwars.developer")
                .handler(this::runCommand)
        );
    }

    private void runCommand(@NotNull CommandContext<PaperPlayerCommandSource> context) {
        final Player player = (Player) context.sender().plattformSender();
        if (BlockListener.enabled) {
            player.sendMessage("§cDas Block-Checking wurde deaktiviert!");
            BlockListener.enabled = false;
        } else {
            player.sendMessage("§cDas Block-Checking wurde aktiviert!");
            BlockListener.enabled = true;
        }

    }
}
