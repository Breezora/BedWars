package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.ConfigurationType;
import net.alphalightning.bedwars.setup.manager.MapSetupManager;
import net.alphalightning.bedwars.setup.map.LobbyConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("createmap")
@CommandPermission("bedwars.admin")
@Description("Erstellt eine neue Map (Lobby/GameMap)")
public class CreateMapCommand extends BaseCommand implements LobbyConfiguration {

    @Dependency private BedWarsPlugin plugin;
    @Dependency private MapSetupManager setupManager;

    @Subcommand("lobby")
    public void onCreateLobby(Player player) {
        setupManager.prepareNewSetup(plugin, ConfigurationType.LOBBY, player, LOBBY_MAP_NAME)
                .startSetup();
    }

    @Subcommand("gamemap")
    public void onCreateGameMap(Player player, @Name("mapName") String mapName) {
        setupManager.prepareNewSetup(plugin, ConfigurationType.MAP, player, mapName)
                .startSetup();
    }
}
