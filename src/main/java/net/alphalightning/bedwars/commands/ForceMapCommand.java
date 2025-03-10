package net.alphalightning.bedwars.commands;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommand;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommandSource;
import net.alphalightning.bedwars.commands.cloud.sender.PaperPlayerCommandSource;
import net.alphalightning.bedwars.game.map.MapManager;
import net.alphalightning.bedwars.game.state.states.LobbyState;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.permission.Permission;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.incendo.cloud.parser.standard.StringParser.stringParser;

public class ForceMapCommand extends PaperCommand<@NotNull BedWarsPlugin> {

    private static final List<Permission> PERMISSIONS = List.of(
            Permission.of("bedwars.command.forcemap"),
            Permission.of("bedwars.admin"),
            Permission.of("bedwars.*")
    );
    private boolean isForced = false;

    public ForceMapCommand(BedWarsPlugin plugin) {
        super(plugin);
    }

    @Override
    public void register(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("forcemap")
                .commandDescription(RichDescription.translatable("command.forcemap.description"))
                .senderType(PaperPlayerCommandSource.class)
                .permission(Permission.anyOf(PERMISSIONS))
                .required("name", stringParser(), suggestions())
                .handler(this::runCommand)
        );
    }

    private SuggestionProvider<PaperPlayerCommandSource> suggestions() {
        if (!(this.plugin.gameStateContext().currentState() instanceof LobbyState lobbyState)) {
            return SuggestionProvider.noSuggestions();
        }
        return SuggestionProvider.suggesting(lobbyState.mapManager().maps()
                .stream().map(map -> Suggestion.suggestion(map.name()))
                .toList());
    }

    private void runCommand(@NotNull CommandContext<PaperPlayerCommandSource> context) {
        Player player = (Player) context.sender().plattformSender();

        if (!(this.plugin.gameStateContext().currentState() instanceof LobbyState lobbyState)) {
            player.sendMessage(Component.translatable("command.forcemap.error.gamestate"));
            return;
        }
        if (this.isForced && !player.hasPermission("bedwars.admin")) {
            player.sendMessage(Component.translatable("command.forcemap.error.used"));
            return;
        }

        String name = context.get("name");

        if (lobbyState.mapManager().selected().name().equalsIgnoreCase(name)) {
            player.sendMessage(Component.translatable("command.forcemap.error.same"));
            return;
        }

        forceMap(lobbyState, player, name);
    }

    private void forceMap(@NotNull LobbyState lobbyState, @NotNull Player player, String name) {
        MapManager manager = lobbyState.mapManager();

        if (!(this.isForced = manager.select(name))) {
            return;
        }

        lobbyState.updateSelectedMap(manager.selected());
        lobbyState.updateMapName(lobbyState);
        player.sendMessage(Component.translatable("command.forcemap.success", NamedTranslationArgument.component("name", Component.text(name))));
    }
}
