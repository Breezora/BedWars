package net.alphalightning.bedwars.commands;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommand;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommandSource;
import net.alphalightning.bedwars.commands.cloud.sender.PaperPlayerCommandSource;
import net.alphalightning.bedwars.game.ui.shop.item.ItemShopGui;
import net.alphalightning.bedwars.game.ui.shop.upgrade.UpgradeShopGui;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.incendo.cloud.parser.standard.StringParser.stringParser;

public class TestGuiCommand extends PaperCommand<@NotNull BedWarsPlugin> {

    private final List<String> names = List.of("item-shop", "upgrade-shop");

    public TestGuiCommand(BedWarsPlugin plugin) {
        super(plugin);
    }

    @Override
    public void register(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("testgui")
                .commandDescription(RichDescription.translatable("command.testgui.description"))
                .senderType(PaperPlayerCommandSource.class)
                .permission("bedwars.*")
                .optional("gui", stringParser(), suggestionProvider())
                .handler(this::runCommand)
        );
    }

    private void runCommand(@NotNull CommandContext<PaperPlayerCommandSource> context) {
        final String gui = context.getOrDefault("gui", "item-shop").toLowerCase();
        final Player player = (Player) context.sender().plattformSender();

        if (gui.equals("item-shop")) {
            new ItemShopGui().showGui(player);
        } else {
            new UpgradeShopGui(player).showGui();
        }
    }

    private @NotNull SuggestionProvider<PaperCommandSource> suggestionProvider() {
        return SuggestionProvider.suggesting(names.stream().map(Suggestion::suggestion).toList());
    }
}
