package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.BuyableItem;
import net.alphalightning.bedwars.game.ui.item.CurrentItem;
import net.alphalightning.bedwars.game.ui.legacy.*;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class FastBuyGui {

    public final Gui gui() {

        String iron = "gui.shop.itemshop.buyable.lore.not-enough-iron";
        String gold = "gui.shop.itemshop.buyable.lore.not-enough-gold";
        //String emerald = "gui.shop.itemshop.buyable.lore.not-enough-emerald";

        return Gui.normal()
                .setStructure(
                        "a . . . . . . . .",
                        ". b d f h j l n .",
                        ". c e g i k m o ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new WoolItem())
                .addIngredient('c', new BuyableItem(Material.OAK_PLANKS, "gui.shop.itemshop.buyable.wood.name", 16,
                        "gui.shop.itemshop.buyable.wood.price",
                        "",
                        "gui.shop.itemshop.buyable.wood.lore",
                        "gui.shop.itemshop.buyable.wood.lore.2",
                        "",
                        gold))
                .addIngredient('d', new BuyableItem(Material.GLASS, "gui.shop.itemshop.buyable.glass.name", 4,
                        "gui.shop.itemshop.buyable.glass.price",
                        "",
                        "gui.shop.itemshop.buyable.glass.lore",
                        "",
                        iron))
                .addIngredient('e', new BuyableItem(Material.END_STONE, "gui.shop.itemshop.buyable.endstone.name", 12,
                        "gui.shop.itemshop.buyable.endstone.price",
                        "",
                        "gui.shop.itemshop.buyable.endstone.lore",
                        "gui.shop.itemshop.buyable.endstone.lore.2",
                        "",
                        iron))
                .addIngredient('f', new StoneSwordItem())
                .addIngredient('g', new IronSwordItem())
                .addIngredient('h', new WoodenPickaxeItem())
                .addIngredient('i', new WoodenAxeItem())
                .addIngredient('j', new InvisPotionItem())
                .addIngredient('k', new SpeedPotionItem())
                .addIngredient('l', new TntItem())
                .addIngredient('m', new BuyableItem(Material.GOLDEN_APPLE, "gui.shop.itemshop.buyable.goldapple.name", 1,
                        "gui.shop.itemshop.buyable.goldapple.price",
                        "",
                        "gui.shop.itemshop.buyable.goldapple.lore",
                        "",
                        gold))
                .addIngredient('n', new BuyableItem(Material.CHAINMAIL_BOOTS, "gui.shop.itemshop.buyable.armor.chain.name", 1,
                        "gui.shop.itemshop.buyable.armor.chain.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.chain.lore",
                        "gui.shop.itemshop.buyable.armor.chain.lore.2",
                        "",
                        iron))
                .addIngredient('o', new BuyableItem(Material.IRON_BOOTS, "gui.shop.itemshop.buyable.armor.iron.name", 1,
                        "gui.shop.itemshop.buyable.armor.iron.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.iron.lore",
                        "gui.shop.itemshop.buyable.armor.iron.lore.2",
                        "",
                        gold))
                .build();
    }

}
