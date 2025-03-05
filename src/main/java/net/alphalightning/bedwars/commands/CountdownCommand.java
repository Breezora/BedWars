package net.alphalightning.bedwars.commands;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommand;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommandSource;
import net.alphalightning.bedwars.commands.cloud.sender.PaperPlayerCommandSource;
import net.alphalightning.bedwars.game.countdown.LobbyCountdown;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.incendo.cloud.parser.standard.IntegerParser.integerParser;
import static org.incendo.cloud.parser.standard.StringParser.stringParser;

public class CountdownCommand extends PaperCommand<@NotNull BedWarsPlugin> {

    private final List<String> names = List.of("lobby");

    public CountdownCommand(@NotNull BedWarsPlugin plugin) {
        super(plugin);
    }

    @Override
    public void register(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("countdown")
                .commandDescription(RichDescription.translatable("command.countdown.description"))
                .senderType(PaperPlayerCommandSource.class)
                .permission("bedwars.*")
                .required("type", stringParser(), suggestionProvider())
                .required("duration", integerParser())
                .handler(this::runCommand));
    }

    private void runCommand(@NotNull CommandContext<PaperPlayerCommandSource> context) {
        final Player player = (Player) context.sender().plattformSender();
        final String countdown = context.get("type");
        final int duration = context.get("duration");

        if (duration <= 0) {
            player.sendMessage(Component.translatable("command.countdown.error"));
            return;
        }

        if (countdown.equalsIgnoreCase("lobby")) {
            new LobbyCountdown(plugin, duration).start();
        }
    }

    private @NotNull SuggestionProvider<PaperCommandSource> suggestionProvider() {
        return SuggestionProvider.suggesting(names.stream().map(Suggestion::suggestion).toList());
    }
}
