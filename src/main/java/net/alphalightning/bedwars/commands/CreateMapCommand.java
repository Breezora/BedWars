package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.exception.AlreadyRegisteredException;
import net.alphalightning.bedwars.manager.PlayerManager;
import net.alphalightning.bedwars.setup.ConfigurationType;
import net.alphalightning.bedwars.setup.Setup;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@CommandAlias("createmap")
@CommandPermission("bedwars.admin")
@Description("Erstellt eine neue Map (Lobby/GameMap)")
public class CreateMapCommand extends BaseCommand {

    @Dependency
    private BedWarsPlugin plugin;

    @Dependency
    private PlayerManager<ConfigurationType> playerManager;

    @Subcommand("lobby")
    public void onCreateLobby(Player player) {
        try {
            playerManager.registerPlayer(player, ConfigurationType.LOBBY);
            Setup.mapBuilder(ConfigurationType.LOBBY)
                    .plugin(plugin)
                    .executor(player)
                    .build().start();

        } catch (AlreadyRegisteredException exception) {
            player.sendMessage(Component.translatable("error.setup.already-running"));
        }
    }

    @Subcommand("gamemap")
    public void onCreateGameMap(Player player, @Name("mapName") String mapName) {
        try {
            playerManager.registerPlayer(player, ConfigurationType.MAP);
            Setup.mapBuilder(ConfigurationType.MAP)
                    .plugin(plugin)
                    .executor(player)
                    .name(mapName)
                    .build().start();

        } catch (AlreadyRegisteredException exception) {
            player.sendMessage(Component.translatable("error.setup.already-running"));
        }
    }
}
