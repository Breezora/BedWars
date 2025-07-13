package net.alphalightning.bedwars.game.ui.shop.item;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.ui.shop.item.items.BuyableItem;
import net.alphalightning.bedwars.game.ui.shop.item.items.CurrentItem;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class WeaponsGui {

    private final BedWarsPlugin plugin;
    private final Gui gui;

    public WeaponsGui(BedWarsPlugin plugin) {
        this.plugin = plugin;
        this.gui = createGui();
    }

    private Gui createGui() {

        return Gui.normal()
                .setStructure(
                        ". . a . . . . . .",
                        ". b c d e . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(plugin, Material.STONE_SWORD, "gui.shop.itemshop.buyable.stonesword.name", 1,
                        "gui.shop.itemshop.buyable.stonesword.price"))
                .addIngredient('c', new BuyableItem(plugin, Material.IRON_SWORD, "gui.shop.itemshop.buyable.ironsword.name", 1,
                        "gui.shop.itemshop.buyable.ironsword.price"))
                .addIngredient('d', new BuyableItem(plugin, Material.DIAMOND_SWORD, "gui.shop.itemshop.buyable.diamondsword.name", 1,
                        "gui.shop.itemshop.buyable.diamondsword.price"))
                .addIngredient('e', new BuyableItem(plugin, Material.STICK, "gui.shop.itemshop.buyable.stick.name", 1,
                        "gui.shop.itemshop.buyable.stick.price"))
                .build();
    }

    public Gui gui() {
        return this.gui;
    }
}
