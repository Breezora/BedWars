package net.alphalightning.bedwars.game.ui.shop.item;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.ui.shop.item.items.BuyablePotionItem;
import net.alphalightning.bedwars.game.ui.shop.item.items.CurrentItem;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import xyz.xenondevs.invui.gui.Gui;

public class PotionsGui {

    private final BedWarsPlugin plugin;
    private final Gui gui;

    public PotionsGui(BedWarsPlugin plugin) {
        this.plugin = plugin;
        this.gui = createGui();
    }


    private Gui createGui() {

        return Gui.normal()
                .setStructure(
                        ". . . . . . a . .",
                        ". b c d . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyablePotionItem(plugin, "gui.shop.itemshop.buyable.potion.speed.name", PotionType.SWIFTNESS, PotionEffectType.SPEED, 45, 1,
                        "gui.shop.itemshop.buyable.potion.speed.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.speed.lore"))
                .addIngredient('c', new BuyablePotionItem(plugin, "gui.shop.itemshop.buyable.potion.jumpboost.name", PotionType.LEAPING, PotionEffectType.JUMP_BOOST, 45, 5,
                        "gui.shop.itemshop.buyable.potion.jumpboost.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.jumpboost.lore"))
                .addIngredient('d', new BuyablePotionItem(plugin, "gui.shop.itemshop.buyable.potion.invisibility.name", PotionType.INVISIBILITY, PotionEffectType.INVISIBILITY, 30, 0,
                        "gui.shop.itemshop.buyable.potion.invisibility.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.invisibility.lore"))
                .build();
    }

    public Gui gui() {
        return this.gui;
    }
}
