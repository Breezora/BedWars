package net.alphalightning.bedwars.game.ui.shop.item;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.ui.shop.item.items.BuyableItem;
import net.alphalightning.bedwars.game.ui.shop.item.items.CurrentItem;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class ArmorGui {

    private final BedWarsPlugin plugin;
    private final Gui gui;

    public ArmorGui(BedWarsPlugin plugin) {
        this.plugin = plugin;
        this.gui = createGui();
    }


    private Gui createGui() {
        return Gui.normal()
                .setStructure(
                        ". . . a . . . . .",
                        ". b c d . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(plugin, Material.CHAINMAIL_BOOTS, "gui.shop.itemshop.buyable.armor.chain.name", 1,
                        "gui.shop.itemshop.buyable.armor.chain.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.chain.lore",
                        "gui.shop.itemshop.buyable.armor.chain.lore.2",
                        "",
                        "gui.shop.itemshop.buyable.lore.not-enough-iron"))
                .addIngredient('c', new BuyableItem(plugin, Material.IRON_BOOTS, "gui.shop.itemshop.buyable.armor.iron.name", 1,
                        "gui.shop.itemshop.buyable.armor.iron.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.iron.lore",
                        "gui.shop.itemshop.buyable.armor.iron.lore.2",
                        "",
                        "gui.shop.itemshop.buyable.lore.not-enough-gold"))
                .addIngredient('d', new BuyableItem(plugin, Material.DIAMOND_BOOTS, "gui.shop.itemshop.buyable.armor.diamond.name", 1,
                        "gui.shop.itemshop.buyable.armor.diamond.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.diamond.lore",
                        "gui.shop.itemshop.buyable.armor.diamond.lore.2",
                        "",
                        "gui.shop.itemshop.buyable.lore.not-enough-emerald"))
                .build();
    }

    public Gui gui() {
        return this.gui;
    }

}
