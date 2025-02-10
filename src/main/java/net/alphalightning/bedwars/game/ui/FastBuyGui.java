package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.legacy.*;
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
                .addIngredient('n', new ChainArmorItem())
                .addIngredient('o', new IronArmorItem())
                .build();
    }

}
