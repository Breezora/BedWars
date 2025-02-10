package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.BuyableItem;
import net.alphalightning.bedwars.game.ui.item.CurrentItem;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;

public class BlocksGui {

    public final Gui gui() {

        String iron = "gui.shop.itemshop.buyable.lore.not-enough-iron";
        String gold = "gui.shop.itemshop.buyable.lore.not-enough-gold";
        String emerald = "gui.shop.itemshop.buyable.lore.not-enough-emerald";

        return Gui.normal()
                .setStructure(
                        ". a . . . . . . .",
                        ". b c d e f g h .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(Material.WHITE_WOOL, "gui.shop.itemshop.buyable.wool.name", 16,
                        "gui.shop.itemshop.buyable.wool.price",
                        "",
                        "gui.shop.itemshop.buyable.wool.lore",
                        "gui.shop.itemshop.buyable.wool.lore.2",
                        "gui.shop.itemshop.buyable.wool.lore.3",
                        iron))
                .addIngredient('c', new BuyableItem(Material.TERRACOTTA, "gui.shop.itemshop.buyable.terracotta.name", 16,
                        "gui.shop.itemshop.buyable.terracotta.price",
                        "",
                        "gui.shop.itemshop.buyable.terracotta.lore",
                        "gui.shop.itemshop.buyable.terracotta.lore.2",
                        "",
                        iron))
                .addIngredient('d', new BuyableItem(Material.GLASS, "gui.shop.itemshop.buyable.glass.name", 4,
                        "gui.shop.itemshop.buyable.glass.price",
                        "",
                        "gui.shop.itemshop.buyable.glass.lore",
                        "",
                        iron))
                .addIngredient('e', new BuyableItem(Material.END_STONE, "gui.shop.itemshop.buyable.endstone.name", 12,
                        "gui.shop.itemshop.buyable.endstone.price",
                        "",
                        "gui.shop.itemshop.buyable.endstone.lore",
                        "gui.shop.itemshop.buyable.endstone.lore.2",
                        "",
                        iron))
                .addIngredient('f', new BuyableItem(Material.LADDER, "gui.shop.itemshop.buyable.ladder.name", 8,
                        "gui.shop.itemshop.buyable.ladder.price",
                        "",
                        "gui.shop.itemshop.buyable.ladder.lore",
                        "gui.shop.itemshop.buyable.ladder.lore.2",
                        "",
                        iron))
                .addIngredient('g', new BuyableItem(Material.OAK_PLANKS, "gui.shop.itemshop.buyable.wood.name", 16,
                        "gui.shop.itemshop.buyable.wood.price",
                        "",
                        "gui.shop.itemshop.buyable.wood.lore",
                        "gui.shop.itemshop.buyable.wood.lore.2",
                        "",
                        gold))
                .addIngredient('h', new BuyableItem(Material.OBSIDIAN, "gui.shop.itemshop.buyable.obsidian.name", 4,
                        "gui.shop.itemshop.buyable.obsidian.price",
                        "",
                        "gui.shop.itemshop.buyable.obsidian.lore",
                        "",
                        emerald))
                .build();
    }

}
