package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.*;
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
                .addIngredient('b', new ChainArmorItem())
                .addIngredient('c', new IronArmorItem())
                .addIngredient('d', new DiamondArmorItem())
                .build();
    }

}
