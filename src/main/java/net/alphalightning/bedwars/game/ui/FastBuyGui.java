package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.BuyableItem;
import net.alphalightning.bedwars.game.ui.legacy.*;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class FastBuyGui {

    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        "a . . . . . . . .",
                        ". b d f h j l n .",
                        ". c e g i k m o ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new WoolItem())
                .addIngredient('c', new PlanksItem())
                .addIngredient('d', new GlassItem())
                .addIngredient('e', new EndstoneItem())
                .addIngredient('f', new StoneSwordItem())
                .addIngredient('g', new IronSwordItem())
                .addIngredient('h', new WoodenPickaxeItem())
                .addIngredient('i', new WoodenAxeItem())
                .addIngredient('j', new InvisPotionItem())
                .addIngredient('k', new SpeedPotionItem())
                .addIngredient('l', new TntItem())
                .addIngredient('m', new GoldenAppleItem())
                .addIngredient('n', new BuyableItem(Material.CHAINMAIL_BOOTS, "gui.shop.itemshop.buyable.armor.chain.name", 1,
                "gui.shop.itemshop.buyable.armor.chain.price",
                "",
                "gui.shop.itemshop.buyable.armor.chain.lore",
                "gui.shop.itemshop.buyable.armor.chain.lore.2",
                "",
                "gui.shop.itemshop.buyable.lore.not-enough-iron"))
                .addIngredient('o', new BuyableItem(Material.IRON_BOOTS, "gui.shop.itemshop.buyable.armor.iron.name", 1,
                        "gui.shop.itemshop.buyable.armor.iron.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.iron.lore",
                        "gui.shop.itemshop.buyable.armor.iron.lore.2",
                        "",
                        "gui.shop.itemshop.buyable.lore.not-enough-gold"))
                .build();
    }

}
