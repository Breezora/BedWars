package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

/**
 * Diese Klasse dient zum Testen der Configurations-UI
 */
@CommandAlias("opengui")
public class OpenGuiCommand extends BaseCommand {

    @Default
    public void onDefault(Player player) {
    }

}
