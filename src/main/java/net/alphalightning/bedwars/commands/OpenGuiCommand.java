package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Subcommand;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.ui.ConfigureItemSpawnerGui;
import net.alphalightning.bedwars.setup.ui.GameMapConfigurationOverviewGui;
import net.alphalightning.bedwars.setup.ui.SelectTeamsGui;
import net.alphalightning.bedwars.setup.ui.simulation.StageSimulation;
import org.bukkit.entity.Player;

/**
 * Diese Klasse dient zum Testen der Configurations-UI
 */
@CommandAlias("opengui")
public class OpenGuiCommand extends BaseCommand {

    @Dependency
    private BedWarsPlugin plugin;

    @Default
    public void onDefault(Player player) {
        new StageSimulation(plugin, player, 1);
        new GameMapConfigurationOverviewGui(player).showGui();
    }

    @Subcommand("itemspawner")
    public void onItemSpawner(Player player) {
        new ConfigureItemSpawnerGui(player).showGui();
    }

    @Subcommand("teams")
    public void onTeams(Player player) {
        new SelectTeamsGui(player).showGui();
    }

}
