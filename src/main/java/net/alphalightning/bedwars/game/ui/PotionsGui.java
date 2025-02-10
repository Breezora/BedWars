package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.CurrentItem;
import net.alphalightning.bedwars.game.ui.legacy.InvisPotionItem;
import net.alphalightning.bedwars.game.ui.legacy.JumpBoostPotionItem;
import net.alphalightning.bedwars.game.ui.legacy.SpeedPotionItem;
import xyz.xenondevs.invui.gui.Gui;

public class PotionsGui {

    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        ". . . . . . a . .",
                        ". b c d . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new SpeedPotionItem())
                .addIngredient('c', new JumpBoostPotionItem())
                .addIngredient('d', new InvisPotionItem())
                .build();
    }

}
