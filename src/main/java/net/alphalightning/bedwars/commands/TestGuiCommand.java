package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.alphalightning.bedwars.game.ui.shop.item.ItemShopGui;
import net.alphalightning.bedwars.game.ui.shop.upgrade.UpgradeShopGui;
import org.bukkit.entity.Player;

@CommandAlias("testgui")
@CommandPermission("bedwars.dev")
@Description("Zeigt testweise eine GUI an.")
public class TestGuiCommand extends BaseCommand {

    @Default
    public void onTestGuiCommand(Player player) {
        new ItemShopGui().showGui(player);
    }

    @Subcommand("upgrade")
    public void onUpgrade(Player player) {
        new UpgradeShopGui(player).showGui();
    }

}
