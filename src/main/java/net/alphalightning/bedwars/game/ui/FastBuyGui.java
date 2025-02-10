package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.BuyableItem;
import net.alphalightning.bedwars.game.ui.item.BuyablePotionItem;
import net.alphalightning.bedwars.game.ui.item.CurrentItem;
import net.alphalightning.bedwars.game.ui.legacy.*;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import xyz.xenondevs.invui.gui.Gui;

public class FastBuyGui {

    public final Gui gui() {

        String level = "gui.shop.itemshop.upgradable.level.1";

        String upgrade = "gui.shop.itemshop.buyable.lore.upgradable";
        String upgrade2 = "gui.shop.itemshop.buyable.lore.upgradable.2";

        String permrespawn = "gui.shop.itemshop.buyable.lore.permrespawn";
        String permrespawn2 = "gui.shop.itemshop.buyable.lore.permrespawn.2";
        String permrespawn3 = "gui.shop.itemshop.buyable.lore.permrespawn.3";

        String iron = "gui.shop.itemshop.buyable.lore.not-enough-iron";
        String gold = "gui.shop.itemshop.buyable.lore.not-enough-gold";
        String emerald = "gui.shop.itemshop.buyable.lore.not-enough-emerald";

        return Gui.normal()
                .setStructure(
                        "a . . . . . . . .",
                        ". b d f h j l n .",
                        ". c e g i k m o ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(Material.WHITE_WOOL, "gui.shop.itemshop.buyable.wool.name", 16,
                        "gui.shop.itemshop.buyable.wool.price",
                        "",
                        "gui.shop.itemshop.buyable.wool.lore",
                        "gui.shop.itemshop.buyable.wool.lore.2",
                        "gui.shop.itemshop.buyable.wool.lore.3",
                        iron))
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
                .addIngredient('h', new BuyableItem(Material.WOODEN_PICKAXE, "gui.shop.itemshop.buyable.woodpickaxe.name", 1,
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
                        iron))
                .addIngredient('i', new BuyableItem(Material.WOODEN_AXE, "gui.shop.itemshop.buyable.woodaxe.name", 1,
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
                        iron))
                .addIngredient('j',  new BuyablePotionItem("gui.shop.itemshop.buyable.potion.invisibility.name", PotionType.INVISIBILITY, PotionEffectType.INVISIBILITY, 30, 0,
                        "gui.shop.itemshop.buyable.potion.invisibility.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.invisibility.lore",
                        "",
                        emerald))
                .addIngredient('k', new BuyablePotionItem("gui.shop.itemshop.buyable.potion.speed.name", PotionType.SWIFTNESS, PotionEffectType.SPEED, 45, 1,
                        "gui.shop.itemshop.buyable.potion.speed.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.speed.lore",
                        "",
                        emerald))
                .addIngredient('l', new BuyableItem(Material.TNT, "gui.shop.itemshop.buyable.tnt.name", 1,
                        "gui.shop.itemshop.buyable.tnt.price",
                        "",
                        "gui.shop.itemshop.buyable.tnt.lore",
                        "gui.shop.itemshop.buyable.tnt.lore.2",
                        "",
                        gold))
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
