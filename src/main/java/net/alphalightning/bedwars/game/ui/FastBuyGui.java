package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.*;
import xyz.xenondevs.invui.gui.Gui;

public class FastBuyGui {

    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        "a . . . . . . . a",
                        ". b d f h . . . .",
                        ". c e g . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new WoolItem())
                .addIngredient('c', new PlanksItem())
                .addIngredient('d', new GlassItem())
                .addIngredient('e', new EndstoneItem())
                .addIngredient('f', new StoneSwordItem())
                .addIngredient('g', new StoneSwordItem())
                .addIngredient('h', new WoodenPickaxeItem())
                .build();
    }

}
