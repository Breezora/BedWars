package net.alphalightning.bedwars.game.ui.shop.item;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.ui.shop.item.items.BuyableItem;
import net.alphalightning.bedwars.game.ui.shop.item.items.CurrentItem;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class BowsGui {

    private final BedWarsPlugin plugin;
    private final Gui gui;

    public BowsGui(BedWarsPlugin plugin) {
        this.plugin = plugin;
        this.gui = createGui();
    }

    private Gui createGui() {
        String gold = "gui.shop.itemshop.buyable.lore.not-enough-gold";
        String emerald = "gui.shop.itemshop.buyable.lore.not-enough-emerald";

        return Gui.normal()
                .setStructure(
                        ". . . . . a . . .",
                        ". b c d e . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(plugin, Material.ARROW, "gui.shop.itemshop.buyable.arrow.name", 6,
                        "gui.shop.itemshop.buyable.arrow.price",
                        "",
                        gold))
                .addIngredient('c', new BuyableItem(plugin, Material.BOW, "gui.shop.itemshop.buyable.bow.name", 1,
                        "gui.shop.itemshop.buyable.bow.price",
                        "",
                        gold))
                .addIngredient('d', new BuyableItem(plugin, Material.BOW, "gui.shop.itemshop.buyable.bow.2.name", 1,
                        "gui.shop.itemshop.buyable.bow.2.price",
                        "",
                        gold))
                .addIngredient('e', new BuyableItem(plugin, Material.BOW, "gui.shop.itemshop.buyable.bow.3.name", 1,
                        "gui.shop.itemshop.buyable.bow.3.price",
                        "",
                        emerald))
                .build();
    }

    public Gui gui() {
        return this.gui;
    }
}
