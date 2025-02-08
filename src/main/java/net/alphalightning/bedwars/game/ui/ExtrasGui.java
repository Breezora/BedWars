package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.*;
import xyz.xenondevs.invui.gui.Gui;

public class ExtrasGui {

    public final Gui gui() {
        return Gui.normal()
                .setStructure(
                        ". . . . . . . a .",
                        ". b c d e f g h .",
                        ". i j k l . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new GoldenAppleItem())
                .addIngredient('c', new BedBugItem())
                .addIngredient('d', new IronGolemItem())
                .addIngredient('e', new FireballItem())
                .addIngredient('f', new TntItem())
                .addIngredient('g', new EnderpearlItem())
                .addIngredient('h', new WaterbucketItem())
                .addIngredient('i', new BridgeEggItem())
                .addIngredient('j', new MagicalMilkItem())
                .addIngredient('k', new SpongeItem())
                .addIngredient('l', new CompactTowerItem())
                .build();
    }

}
