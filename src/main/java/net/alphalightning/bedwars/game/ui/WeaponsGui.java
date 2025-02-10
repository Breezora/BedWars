package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.legacy.*;
import xyz.xenondevs.invui.gui.Gui;

public class WeaponsGui {

    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        ". . a . . . . . .",
                        ". b c d e . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new StoneSwordItem())
                .addIngredient('c', new IronSwordItem())
                .addIngredient('d', new DiamondSwordItem())
                .addIngredient('e', new StickItem())
                .build();
    }


}
