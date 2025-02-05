package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.*;
import xyz.xenondevs.invui.gui.Gui;

public class FastBuyGui {

    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        "a . . . . . . . a",
                        ". b . . . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new WoolItem())
                .build();
    }

}
