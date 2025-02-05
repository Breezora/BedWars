package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import net.alphalightning.bedwars.game.ui.ItemShopGui;
import org.bukkit.entity.Player;

@CommandAlias("testgui")
@CommandPermission("bedwars.dev")
@Description("Zeigt testweise eine GUI an.")
public class TestGuiCommand extends BaseCommand {

    ItemShopGui gui = new ItemShopGui();

    public void onTestGuiCommand(Player player) {
        gui.showGui(player);
    }

}
