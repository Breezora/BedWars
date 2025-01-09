package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import net.alphalightning.bedwars.setup.ConfigurationType;
import net.alphalightning.bedwars.setup.Setup;
import net.alphalightning.bedwars.setup.map.LobbyMapSetupBuilder;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.entity.Player;

// Start debug

@CommandAlias("create")
public class CreateCommand extends BaseCommand {

    @Subcommand("lobby")
    public void onCreateLobby(Player player, int stage) {
        MapSetup.Builder builder = Setup.mapBuilder(ConfigurationType.LOBBY);

        if (builder instanceof LobbyMapSetupBuilder lobbyBuilder) {
            lobbyBuilder.stage(stage);
        }

        builder.executor(player)
                .build()
                .start();
    }

}
