package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.BuyableItem;
import net.alphalightning.bedwars.game.ui.item.CurrentItem;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class ToolsGui {

    public final Gui gui() {

        String level = "gui.shop.itemshop.upgradable.level.1";

        String upgrade = "gui.shop.itemshop.buyable.lore.upgradable";
        String upgrade2 = "gui.shop.itemshop.buyable.lore.upgradable.2";

        String permrespawn = "gui.shop.itemshop.buyable.lore.permrespawn";
        String permrespawn2 = "gui.shop.itemshop.buyable.lore.permrespawn.2";
        String permrespawn3 = "gui.shop.itemshop.buyable.lore.permrespawn.3";


        return Gui.normal()
                .setStructure(
                        ". . . . a . . . .",
                        ". b c d . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(Material.SHEARS, "gui.shop.itemshop.buyable.shears.name", 1,
                        "gui.shop.itemshop.buyable.shears.price",
                        "",
                        "gui.shop.itemshop.buyable.shears.lore",
                        "gui.shop.itemshop.buyable.shears.lore.2",
                        "",
                        "gui.shop.itemshop.buyable.lore.not-enough-iron"))
                .addIngredient('c', new BuyableItem(Material.WOODEN_PICKAXE, "gui.shop.itemshop.buyable.woodpickaxe.name", 1,
                        "gui.shop.itemshop.buyable.woodpickaxe.price",
                        level,
                        "",
                        upgrade,
                        upgrade2,
                        "",
                        permrespawn,
                        permrespawn2,
                        permrespawn3,
                        "",
                        "gui.shop.itemshop.buyable.lore.not-enough-iron"))
                .addIngredient('d', new BuyableItem(Material.WOODEN_AXE, "gui.shop.itemshop.buyable.woodaxe.name", 1,
                        "gui.shop.itemshop.buyable.woodaxe.price",
                        level,
                        "",
                        upgrade,
                        upgrade2,
                        "",
                        permrespawn,
                        permrespawn2,
                        permrespawn3,
                        "",
                        "gui.shop.itemshop.buyable.lore.not-enough-iron"))
                .build();
    }

}
