package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.legacy.*;
import xyz.xenondevs.invui.gui.Gui;

public class BlocksGui {

    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        ". a . . . . . . .",
                        ". b c d e f g h .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new WoolItem())
                .addIngredient('c', new TerracottaItem())
                .addIngredient('d', new GlassItem())
                .addIngredient('e', new EndstoneItem())
                .addIngredient('f', new LadderItem())
                .addIngredient('g', new PlanksItem())
                .addIngredient('h', new ObsidianItem())
                .build();
    }

}
