package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.BuyableItem;
import net.alphalightning.bedwars.game.ui.item.CurrentItem;
import net.alphalightning.bedwars.game.ui.legacy.*;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class ExtrasGui {

    public final Gui gui() {

        String iron = "gui.shop.itemshop.buyable.lore.not-enough-iron";
        String gold = "gui.shop.itemshop.buyable.lore.not-enough-gold";
        String emerald = "gui.shop.itemshop.buyable.lore.not-enough-emerald";

        return Gui.normal()
                .setStructure(
                        ". . . . . . . a .",
                        ". b c d e f g h .",
                        ". i j k l . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(Material.GOLDEN_APPLE, "gui.shop.itemshop.buyable.goldapple.name", 1,
                        "gui.shop.itemshop.buyable.goldapple.price",
                        "",
                        "gui.shop.itemshop.buyable.goldapple.lore",
                        "",
                        gold))
                .addIngredient('c', new BuyableItem(Material.SNOWBALL, "gui.shop.itemshop.buyable.bedbug.name", 1,
                        "gui.shop.itemshop.buyable.bedbug.price",
                        "",
                        "gui.shop.itemshop.buyable.bedbug.lore",
                        "gui.shop.itemshop.buyable.bedbug.lore.2",
                        "gui.shop.itemshop.buyable.bedbug.lore.3",
                        "",
                        iron))
                .addIngredient('d', new BuyableItem(Material.IRON_GOLEM_SPAWN_EGG, "gui.shop.itemshop.buyable.golem.name", 1,
                        "gui.shop.itemshop.buyable.golem.price",
                        "",
                        "gui.shop.itemshop.buyable.golem.lore",
                        "gui.shop.itemshop.buyable.golem.lore.2",
                        "",
                        iron))
                .addIngredient('e', new BuyableItem(Material.FIRE_CHARGE, "gui.shop.itemshop.buyable.fireball.name", 1,
                        "gui.shop.itemshop.buyable.fireball.price",
                        "",
                        "gui.shop.itemshop.buyable.fireball.lore",
                        "gui.shop.itemshop.buyable.fireball.lore.2",
                        "gui.shop.itemshop.buyable.fireball.lore.3",
                        "",
                        iron))
                .addIngredient('f', new BuyableItem(Material.TNT, "gui.shop.itemshop.buyable.tnt.name", 1,
                        "gui.shop.itemshop.buyable.tnt.price",
                        "",
                        "gui.shop.itemshop.buyable.tnt.lore",
                        "gui.shop.itemshop.buyable.tnt.lore.2",
                        "",
                        gold))
                .addIngredient('g', new BuyableItem(Material.ENDER_PEARL, "gui.shop.itemshop.buyable.enderpearl.name", 1,
                        "gui.shop.itemshop.buyable.enderpearl.price",
                        "",
                        "gui.shop.itemshop.buyable.enderpearl.lore",
                        "gui.shop.itemshop.buyable.enderpearl.lore.2",
                        "",
                        emerald))
                .addIngredient('h', new BuyableItem(Material.WATER_BUCKET, "gui.shop.itemshop.buyable.waterbucket.name", 1,
                        "gui.shop.itemshop.buyable.waterbucket.price",
                        "",
                        "gui.shop.itemshop.buyable.waterbucket.lore",
                        "gui.shop.itemshop.buyable.waterbucket.lore.2",
                        "gui.shop.itemshop.buyable.waterbucket.lore.3",
                        "",
                        gold))
                .addIngredient('i', new BuyableItem(Material.EGG, "gui.shop.itemshop.buyable.bridgeegg.name", 1,
                        "gui.shop.itemshop.buyable.bridgeegg.price",
                        "",
                        "gui.shop.itemshop.buyable.bridgeegg.lore",
                        "gui.shop.itemshop.buyable.bridgeegg.lore.2",
                        "",
                        emerald))
                .addIngredient('j', new BuyableItem(Material.MILK_BUCKET, "gui.shop.itemshop.buyable.magicalmilk.name", 1,
                        "gui.shop.itemshop.buyable.magicalmilk.price",
                        "",
                        "gui.shop.itemshop.buyable.magicalmilk.lore",
                        "gui.shop.itemshop.buyable.magicalmilk.lore.2",
                        "",
                        gold))
                .addIngredient('k', new BuyableItem(Material.SPONGE, "gui.shop.itemshop.buyable.sponge.name", 4,
                        "gui.shop.itemshop.buyable.sponge.price",
                        "",
                        "gui.shop.itemshop.buyable.sponge.lore",
                        "",
                        gold))
                .addIngredient('l', new BuyableItem(Material.CHEST, "gui.shop.itemshop.buyable.compacttower.name", 1,
                        "gui.shop.itemshop.buyable.compacttower.price",
                        "",
                        "gui.shop.itemshop.buyable.compacttower.lore",
                        "",
                        iron))
                .build();
    }

}
