package net.alphalightning.bedwars.game.ui.shop.item;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.ui.shop.item.items.BuyableItem;
import net.alphalightning.bedwars.game.ui.shop.item.items.CurrentItem;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class ToolsGui {

    private final BedWarsPlugin plugin;
    private final Gui gui;

    public ToolsGui(BedWarsPlugin plugin) {
        this.plugin = plugin;
        this.gui = createGui();
    }


    private Gui createGui() {

        String upgrade = "gui.shop.itemshop.buyable.lore.upgradable";
        String upgrade2 = "gui.shop.itemshop.buyable.lore.upgradable.2";

        String permrespawn = "gui.shop.itemshop.buyable.lore.permrespawn";
        String permrespawn2 = "gui.shop.itemshop.buyable.lore.permrespawn.2";
        String permrespawn3 = "gui.shop.itemshop.buyable.lore.permrespawn.3";


        return Gui.normal()
                .setStructure(
                        ". . . . a . . . .",
                        ". b c d . . . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(plugin, Material.SHEARS, "gui.shop.itemshop.buyable.shears.name", 1,
                        "gui.shop.itemshop.buyable.shears.price",
                        "",
                        "gui.shop.itemshop.buyable.shears.lore",
                        "gui.shop.itemshop.buyable.shears.lore.2"))
                .addIngredient('c', new BuyableItem(plugin, Material.WOODEN_PICKAXE, "gui.shop.itemshop.buyable.woodpickaxe.name", 1,
                        "gui.shop.itemshop.buyable.woodpickaxe.price",
                        "",
                        upgrade,
                        upgrade2,
                        "",
                        permrespawn,
                        permrespawn2,
                        permrespawn3))
                .addIngredient('d', new BuyableItem(plugin, Material.WOODEN_AXE, "gui.shop.itemshop.buyable.woodaxe.name", 1,
                        "gui.shop.itemshop.buyable.woodaxe.price",
                        "",
                        upgrade,
                        upgrade2,
                        "",
                        permrespawn,
                        permrespawn2,
                        permrespawn3))
                .build();
    }

    public Gui gui() {
        return this.gui;
    }
}
