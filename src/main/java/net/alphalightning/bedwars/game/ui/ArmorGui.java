package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.BuyableItem;
import net.alphalightning.bedwars.game.ui.legacy.CurrentItem;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class ArmorGui {



    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        ". . . a . . . . .",
                        ". b c d . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(Material.CHAINMAIL_BOOTS, "gui.shop.itemshop.buyable.armor.chain.name", 1,
                        "gui.shop.itemshop.buyable.armor.chain.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.chain.lore",
                        "gui.shop.itemshop.buyable.armor.chain.lore.2",
                        "",
                        "gui.shop.itemshop.buyable.lore.not-enough-iron"))
                .addIngredient('c', new BuyableItem(Material.IRON_BOOTS, "gui.shop.itemshop.buyable.armor.iron.name", 1,
                        "gui.shop.itemshop.buyable.armor.iron.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.iron.lore",
                        "gui.shop.itemshop.buyable.armor.iron.lore.2",
                        "",
                        "gui.shop.itemshop.buyable.lore.not-enough-gold"))
                .addIngredient('d', new BuyableItem(Material.DIAMOND_BOOTS, "gui.shop.itemshop.buyable.armor.diamond.name", 1,
                        "gui.shop.itemshop.buyable.armor.diamond.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.diamond.lore",
                        "gui.shop.itemshop.buyable.armor.diamond.lore.2",
                        "",
                        "gui.shop.itemshop.buyable.lore.not-enough-emerald"))
                .build();
    }

}
