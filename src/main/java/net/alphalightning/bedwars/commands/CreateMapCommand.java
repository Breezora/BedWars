package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.alphalightning.bedwars.setup.ConfigurationType;
import net.alphalightning.bedwars.setup.Setup;
import org.bukkit.entity.Player;

@CommandAlias("createmap")
@CommandPermission("bedwars.admin")
@Description("Erstellt eine neue Map (Lobby/GameMap)")
public class CreateMapCommand extends BaseCommand {

    @Subcommand("lobby")
    public void onCreateLobby(Player player) {
        Setup.mapBuilder(ConfigurationType.LOBBY)
                .executor(player)
                .build().start();
    }

    @Subcommand("gamemap")
    public void onCreateGameMap(Player player, @Name("mapName") String mapName) {
        Setup.mapBuilder(ConfigurationType.MAP)
                .executor(player)
                .name(mapName)
                .build().start();
    }
}
