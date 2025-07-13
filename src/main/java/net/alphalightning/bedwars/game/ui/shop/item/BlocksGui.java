package net.alphalightning.bedwars.game.ui.shop.item;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.ui.shop.item.items.BuyableItem;
import net.alphalightning.bedwars.game.ui.shop.item.items.CurrentItem;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class BlocksGui {

    private final BedWarsPlugin plugin;
    private final Gui gui;


    public BlocksGui(BedWarsPlugin plugin) {
        this.plugin = plugin;
        this.gui = createGui();
    }


    private Gui createGui() {

        return Gui.normal()
                .setStructure(
                        ". a . . . . . . .",
                        ". b c d e f g h .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(plugin, Material.WHITE_WOOL, "gui.shop.itemshop.buyable.wool.name", 16,
                        "gui.shop.itemshop.buyable.wool.price",
                        "",
                        "gui.shop.itemshop.buyable.wool.lore",
                        "gui.shop.itemshop.buyable.wool.lore.2",
                        "gui.shop.itemshop.buyable.wool.lore.3"))
                .addIngredient('c', new BuyableItem(plugin, Material.TERRACOTTA, "gui.shop.itemshop.buyable.terracotta.name", 16,
                        "gui.shop.itemshop.buyable.terracotta.price",
                        "",
                        "gui.shop.itemshop.buyable.terracotta.lore",
                        "gui.shop.itemshop.buyable.terracotta.lore.2"))
                .addIngredient('d', new BuyableItem(plugin, Material.GLASS, "gui.shop.itemshop.buyable.glass.name", 4,
                        "gui.shop.itemshop.buyable.glass.price",
                        "",
                        "gui.shop.itemshop.buyable.glass.lore"))
                .addIngredient('e', new BuyableItem(plugin, Material.END_STONE, "gui.shop.itemshop.buyable.endstone.name", 12,
                        "gui.shop.itemshop.buyable.endstone.price",
                        "",
                        "gui.shop.itemshop.buyable.endstone.lore",
                        "gui.shop.itemshop.buyable.endstone.lore.2"))
                .addIngredient('f', new BuyableItem(plugin, Material.LADDER, "gui.shop.itemshop.buyable.ladder.name", 8,
                        "gui.shop.itemshop.buyable.ladder.price",
                        "",
                        "gui.shop.itemshop.buyable.ladder.lore",
                        "gui.shop.itemshop.buyable.ladder.lore.2"))
                .addIngredient('g', new BuyableItem(plugin, Material.OAK_PLANKS, "gui.shop.itemshop.buyable.wood.name", 16,
                        "gui.shop.itemshop.buyable.wood.price",
                        "",
                        "gui.shop.itemshop.buyable.wood.lore",
                        "gui.shop.itemshop.buyable.wood.lore.2"))
                .addIngredient('h', new BuyableItem(plugin, Material.OBSIDIAN, "gui.shop.itemshop.buyable.obsidian.name", 4,
                        "gui.shop.itemshop.buyable.obsidian.price",
                        "",
                        "gui.shop.itemshop.buyable.obsidian.lore"))
                .build();
    }

    public Gui gui() {
        return this.gui;
    }

}
