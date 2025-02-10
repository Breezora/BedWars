package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.BuyableItem;
import net.alphalightning.bedwars.game.ui.item.CurrentItem;
import net.alphalightning.bedwars.game.ui.legacy.*;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class WeaponsGui {

    public final Gui gui() {

        String iron = "gui.shop.itemshop.buyable.lore.not-enough-iron";
        String gold = "gui.shop.itemshop.buyable.lore.not-enough-gold";
        String emerald = "gui.shop.itemshop.buyable.lore.not-enough-emerald";

        return Gui.normal()
                .setStructure(
                        ". . a . . . . . .",
                        ". b c d e . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(Material.STONE_SWORD, "gui.shop.itemshop.buyable.stonesword.name", 1,
                        "gui.shop.itemshop.buyable.stonesword.price",
                        "",
                        iron))
                .addIngredient('c', new BuyableItem(Material.IRON_SWORD, "gui.shop.itemshop.buyable.ironsword.name", 1,
                        "gui.shop.itemshop.buyable.ironsword.price",
                        "",
                        gold))
                .addIngredient('d', new BuyableItem(Material.DIAMOND_SWORD, "gui.shop.itemshop.buyable.diamondsword.name", 1,
                        "gui.shop.itemshop.buyable.diamondsword.price",
                        "",
                        emerald))
                .addIngredient('e', new BuyableItem(Material.STICK, "gui.shop.itemshop.buyable.stick.name", 1,
                        "gui.shop.itemshop.buyable.stick.price",
                        "",
                        gold))
                .build();
    }


}
