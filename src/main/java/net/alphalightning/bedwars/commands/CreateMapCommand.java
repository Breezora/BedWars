package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.exception.AlreadyRegisteredException;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.manager.PlayerManager;
import net.alphalightning.bedwars.manager.TextManager;
import net.alphalightning.bedwars.setup.ConfigurationType;
import net.alphalightning.bedwars.setup.Setup;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("createmap")
@CommandPermission("bedwars.admin")
@Description("Erstellt eine neue Map (Lobby/GameMap)")
public class CreateMapCommand extends BaseCommand {

    @Dependency private BedWarsPlugin plugin;
    @Dependency private PlayerManager<ConfigurationType> playerManager;
    @Dependency private TextManager mapNameManager;

    @Subcommand("lobby")
    public void onCreateLobby(Player player) {
        try {
            playerManager.registerPlayer(player, ConfigurationType.LOBBY);

        } catch (AlreadyRegisteredException exception) {
            player.sendMessage(Component.translatable("error.setup.already-running"));
            Feedback.error(player);
            return;
        }
        try {
            mapNameManager.registerText("lobby");
            Setup.mapBuilder(ConfigurationType.LOBBY)
                    .plugin(plugin)
                    .executor(player)
                    .build().start();

        } catch (AlreadyRegisteredException exception) {
            player.sendMessage(Component.translatable("error.setup.lobby.running"));
            Feedback.error(player);
        }
    }

    @Subcommand("gamemap")
    public void onCreateGameMap(Player player, @Name("mapName") @NotNull String mapName) {
        try {
            playerManager.registerPlayer(player, ConfigurationType.MAP);

        } catch (AlreadyRegisteredException exception) {
            player.sendMessage(Component.translatable("error.setup.already-running"));
            Feedback.error(player);
            return;
        }
        try {
            mapNameManager.registerText(mapName.toLowerCase());
            Setup.mapBuilder(ConfigurationType.MAP)
                    .plugin(plugin)
                    .executor(player)
                    .name(mapName)
                    .build().start();

        } catch (AlreadyRegisteredException exception) {
            player.sendMessage(Component.translatable("error.setup.map.exists", Component.text(mapName)));
            Feedback.error(player);
        }
    }
}
