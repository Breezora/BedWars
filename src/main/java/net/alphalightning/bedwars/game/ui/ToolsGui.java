package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.CurrentItem;
import net.alphalightning.bedwars.game.ui.legacy.ShearsItem;
import net.alphalightning.bedwars.game.ui.legacy.WoodenAxeItem;
import net.alphalightning.bedwars.game.ui.legacy.WoodenPickaxeItem;
import xyz.xenondevs.invui.gui.Gui;

public class ToolsGui {

    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        ". . . . a . . . .",
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
