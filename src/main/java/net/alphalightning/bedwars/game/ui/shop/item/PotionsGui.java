package net.alphalightning.bedwars.game.ui.shop.item;

import net.alphalightning.bedwars.game.ui.shop.item.items.BuyablePotionItem;
import net.alphalightning.bedwars.game.ui.shop.item.items.CurrentItem;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import xyz.xenondevs.invui.gui.Gui;

public class PotionsGui {

    private final Gui gui;

    public PotionsGui() {
        this.gui = createGui();
    }


    private Gui createGui() {

        String emerald = "gui.shop.itemshop.buyable.lore.not-enough-emerald";

        return Gui.normal()
                .setStructure(
                        ". . . . . . a . .",
                        ". b c d . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyablePotionItem("gui.shop.itemshop.buyable.potion.speed.name", PotionType.SWIFTNESS, PotionEffectType.SPEED, 45, 1,
                        "gui.shop.itemshop.buyable.potion.speed.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.speed.lore",
                        "",
                        emerald))
                .addIngredient('c', new BuyablePotionItem("gui.shop.itemshop.buyable.potion.jumpboost.name", PotionType.LEAPING, PotionEffectType.JUMP_BOOST, 45, 5,
                        "gui.shop.itemshop.buyable.potion.jumpboost.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.jumpboost.lore",
                        "",
                        emerald))
                .addIngredient('d', new BuyablePotionItem("gui.shop.itemshop.buyable.potion.invisibility.name", PotionType.INVISIBILITY, PotionEffectType.INVISIBILITY, 30, 0,
                        "gui.shop.itemshop.buyable.potion.invisibility.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.invisibility.lore",
                        "",
                        emerald))
                .build();
    }

    public Gui gui() {
        return this.gui;
    }
}
