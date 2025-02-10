package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.BuyableItem;
import net.alphalightning.bedwars.game.ui.legacy.ArrowItem;
import net.alphalightning.bedwars.game.ui.legacy.BowItem;
import net.alphalightning.bedwars.game.ui.item.CurrentItem;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class BowsGui {

    public final Gui gui() {

        String gold = "gui.shop.itemshop.buyable.lore.not-enough-gold";
        String emerald = "gui.shop.itemshop.buyable.lore.not-enough-emerald";

        return Gui.normal()
                .setStructure(
                        ". . . . . a . . .",
                        ". b c d e . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new ArrowItem())
                .addIngredient('c', new BuyableItem(Material.BOW, "gui.shop.itemshop.buyable.bow.name", 1,
                        "gui.shop.itemshop.buyable.bow.price",
                        "",
                        gold))
                .addIngredient('d', new BuyableItem(Material.BOW, "gui.shop.itemshop.buyable.bow.2.name", 1,
                        "gui.shop.itemshop.buyable.bow.2.price",
                        "",
                        gold))
                .addIngredient('e', new BuyableItem(Material.BOW, "gui.shop.itemshop.buyable.bow.3.name", 1,
                        "gui.shop.itemshop.buyable.bow.3.price",
                        "",
                        emerald))
                .build();
    }

}
