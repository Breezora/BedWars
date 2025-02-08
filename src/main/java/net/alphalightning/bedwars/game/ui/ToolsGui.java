package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.*;
import xyz.xenondevs.invui.gui.Gui;

public class ToolsGui {

    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        ". . . a . . . . .",
                        ". b c d . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new ShearsItem())
                .addIngredient('c', new WoodenPickaxeItem())
                .addIngredient('d', new WoodenAxeItem())
                .build();
    }

}
