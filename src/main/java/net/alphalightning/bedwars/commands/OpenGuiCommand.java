package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import net.alphalightning.bedwars.setup.ui.ConfigureItemSpawnerGui;
import net.alphalightning.bedwars.setup.ui.GameMapConfigurationOverviewGui;
import org.bukkit.entity.Player;

/**
 * Diese Klasse dient zum Testen der Configurations-UI
 */
@CommandAlias("opengui")
public class OpenGuiCommand extends BaseCommand {

    @Default
    public void onDefault(Player player) {
        new GameMapConfigurationOverviewGui(player).showGui();
    }

    @Subcommand("itemspawner")
    public void onItemSpawner(Player player) {
        new ConfigureItemSpawnerGui(player).showGui();
    }

}
