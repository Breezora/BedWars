package net.alphalightning.bedwars.commands;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommand;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommandSource;
import net.alphalightning.bedwars.commands.cloud.sender.PaperPlayerCommandSource;
import net.alphalightning.bedwars.setup.ConfigurationType;
import net.alphalightning.bedwars.setup.manager.MapSetupManager;
import net.alphalightning.bedwars.setup.map.LobbyConfiguration;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import static org.incendo.cloud.parser.standard.StringParser.stringParser;

public class CreateMapCommand extends PaperCommand<@NonNull BedWarsPlugin> implements LobbyConfiguration {

    private final MapSetupManager setupManager;

    public CreateMapCommand(@NonNull BedWarsPlugin plugin, @NotNull MapSetupManager setupManager) {
        super(plugin);
        this.setupManager = setupManager;
    }

    @Override
    public void register(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("createmap <type> [name]")
                .commandDescription(RichDescription.translatable("command.createmap.description"))
                .senderType(PaperPlayerCommandSource.class)
                .required("type", stringParser(), RichDescription.translatable("command.createmap.argument"), suggestionProvider())
                .optional("name", stringParser())
                .handler(this::runCommand)
        );
    }

    private void runCommand(@NotNull CommandContext<PaperPlayerCommandSource> context) {
        final String type = context.getOrDefault("type", "lobby");
        final Player player = (Player) context.sender();

        if (type.equals("lobby")) {
            setupManager.prepareNewSetup(plugin, ConfigurationType.LOBBY, player, LOBBY_MAP_NAME)
                    .startSetup();
        } else {
            setupManager.prepareNewSetup(plugin, ConfigurationType.MAP, player, context.get("name"))
                    .startSetup();
        }
    }

    private @NotNull SuggestionProvider<PaperCommandSource> suggestionProvider() {
        return SuggestionProvider.suggesting(
          Suggestion.suggestion("lobby"),
          Suggestion.suggestion("gamemap")
        );
    }
}
