package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.BuyablePotionItem;
import net.alphalightning.bedwars.game.ui.item.CurrentItem;
import net.alphalightning.bedwars.game.ui.legacy.InvisPotionItem;
import net.alphalightning.bedwars.game.ui.legacy.JumpBoostPotionItem;
import net.alphalightning.bedwars.game.ui.legacy.SpeedPotionItem;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import xyz.xenondevs.invui.gui.Gui;

public class PotionsGui {

    public final Gui gui() {

        String emerald = "gui.shop.itemshop.buyable.lore.not-enough-emerald";

        return Gui.normal()
                .setStructure(
                        ". . . . . . a . .",
                        ". b c d . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyablePotionItem("gui.shop.itemshop.buyable.potion.speed.name", PotionType.SWIFTNESS, PotionEffectType.SPEED, 45, 1,
                        "gui.shop.itemshop.buyable.potion.speed.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.speed.lore",
                        "",
                        emerald))
                .addIngredient('c', new BuyablePotionItem("gui.shop.itemshop.buyable.potion.jumpboost.name", PotionType.WATER_BREATHING, PotionEffectType.JUMP_BOOST, 45, 5,
                        "gui.shop.itemshop.buyable.potion.jumpboost.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.jumpboost.lore",
                        "",
                        emerald))
                .addIngredient('d', new BuyablePotionItem("gui.shop.itemshop.buyable.potion.invisibility.name", PotionType.INVISIBILITY, PotionEffectType.INVISIBILITY, 30, 0,
                        "gui.shop.itemshop.buyable.potion.invisibility.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.invisibility.lore",
                        "",
                        emerald))
                .build();
    }

}
