package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.legacy.ArrowItem;
import net.alphalightning.bedwars.game.ui.legacy.BowItem;
import net.alphalightning.bedwars.game.ui.legacy.CurrentItem;
import xyz.xenondevs.invui.gui.Gui;

public class BowsGui {

    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        ". . . . . a . . .",
                        ". b c d e . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new ArrowItem())
                .addIngredient('c', new BowItem(1))
                .addIngredient('d', new BowItem(2))
                .addIngredient('e', new BowItem(3))
                .build();
    }

}
