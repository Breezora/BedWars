package net.alphalightning.bedwars.game.ui.shop.item;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.ui.shop.item.items.BuyableItem;
import net.alphalightning.bedwars.game.ui.shop.item.items.BuyablePotionItem;
import net.alphalightning.bedwars.game.ui.shop.item.items.CurrentItem;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import xyz.xenondevs.invui.gui.Gui;

public class FastBuyGui {

    private final BedWarsPlugin plugin;
    private final Gui gui;


    public FastBuyGui(BedWarsPlugin plugin) {
        this.plugin = plugin;
        this.gui = createGui();
    }

    private Gui createGui() {

        String level = "gui.shop.itemshop.upgradable.level.1";

        String upgrade = "gui.shop.itemshop.buyable.lore.upgradable";
        String upgrade2 = "gui.shop.itemshop.buyable.lore.upgradable.2";

        String permrespawn = "gui.shop.itemshop.buyable.lore.permrespawn";
        String permrespawn2 = "gui.shop.itemshop.buyable.lore.permrespawn.2";
        String permrespawn3 = "gui.shop.itemshop.buyable.lore.permrespawn.3";


        return Gui.normal()
                .setStructure(
                        "a . . . . . . . .",
                        ". b d f h j l n .",
                        ". c e g i k m o ."
                )
                .addIngredient('a', new CurrentItem())
                .addIngredient('b', new BuyableItem(plugin, Material.WHITE_WOOL, "gui.shop.itemshop.buyable.wool.name", 16,
                        "gui.shop.itemshop.buyable.wool.price",
                        "",
                        "gui.shop.itemshop.buyable.wool.lore",
                        "gui.shop.itemshop.buyable.wool.lore.2",
                        "gui.shop.itemshop.buyable.wool.lore.3"))
                .addIngredient('c', new BuyableItem(plugin, Material.OAK_PLANKS, "gui.shop.itemshop.buyable.wood.name", 16,
                        "gui.shop.itemshop.buyable.wood.price",
                        "",
                        "gui.shop.itemshop.buyable.wood.lore",
                        "gui.shop.itemshop.buyable.wood.lore.2"))
                .addIngredient('d', new BuyableItem(plugin, Material.GLASS, "gui.shop.itemshop.buyable.glass.name", 4,
                        "gui.shop.itemshop.buyable.glass.price",
                        "",
                        "gui.shop.itemshop.buyable.glass.lore"))
                .addIngredient('e', new BuyableItem(plugin, Material.END_STONE, "gui.shop.itemshop.buyable.endstone.name", 12,
                        "gui.shop.itemshop.buyable.endstone.price",
                        "",
                        "gui.shop.itemshop.buyable.endstone.lore",
                        "gui.shop.itemshop.buyable.endstone.lore.2"))
                .addIngredient('f', new BuyableItem(plugin, Material.STONE_SWORD, "gui.shop.itemshop.buyable.stonesword.name", 1,
                        "gui.shop.itemshop.buyable.stonesword.price"))
                .addIngredient('g', new BuyableItem(plugin, Material.IRON_SWORD, "gui.shop.itemshop.buyable.ironsword.name", 1,
                        "gui.shop.itemshop.buyable.ironsword.price"))
                .addIngredient('h', new BuyableItem(plugin, Material.WOODEN_PICKAXE, "gui.shop.itemshop.buyable.woodpickaxe.name", 1,
                        "gui.shop.itemshop.buyable.woodpickaxe.price",
                        level,
                        "",
                        upgrade,
                        upgrade2,
                        "",
                        permrespawn,
                        permrespawn2,
                        permrespawn3))
                .addIngredient('i', new BuyableItem(plugin, Material.WOODEN_AXE, "gui.shop.itemshop.buyable.woodaxe.name", 1,
                        "gui.shop.itemshop.buyable.woodaxe.price",
                        level,
                        "",
                        upgrade,
                        upgrade2,
                        "",
                        permrespawn,
                        permrespawn2,
                        permrespawn3))
                .addIngredient('j',  new BuyablePotionItem(plugin, "gui.shop.itemshop.buyable.potion.invisibility.name", PotionType.INVISIBILITY, PotionEffectType.INVISIBILITY, 30, 0,
                        "gui.shop.itemshop.buyable.potion.invisibility.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.invisibility.lore"))
                .addIngredient('k', new BuyablePotionItem(plugin, "gui.shop.itemshop.buyable.potion.speed.name", PotionType.SWIFTNESS, PotionEffectType.SPEED, 45, 1,
                        "gui.shop.itemshop.buyable.potion.speed.price",
                        "",
                        "gui.shop.itemshop.buyable.potion.speed.lore"))
                .addIngredient('l', new BuyableItem(plugin, Material.TNT, "gui.shop.itemshop.buyable.tnt.name", 1,
                        "gui.shop.itemshop.buyable.tnt.price",
                        "",
                        "gui.shop.itemshop.buyable.tnt.lore",
                        "gui.shop.itemshop.buyable.tnt.lore.2"))
                .addIngredient('m', new BuyableItem(plugin, Material.GOLDEN_APPLE, "gui.shop.itemshop.buyable.goldapple.name", 1,
                        "gui.shop.itemshop.buyable.goldapple.price",
                        "",
                        "gui.shop.itemshop.buyable.goldapple.lore"))
                .addIngredient('n', new BuyableItem(plugin, Material.CHAINMAIL_BOOTS, "gui.shop.itemshop.buyable.armor.chain.name", 1,
                        "gui.shop.itemshop.buyable.armor.chain.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.chain.lore",
                        "gui.shop.itemshop.buyable.armor.chain.lore.2"))
                .addIngredient('o', new BuyableItem(plugin, Material.IRON_BOOTS, "gui.shop.itemshop.buyable.armor.iron.name", 1,
                        "gui.shop.itemshop.buyable.armor.iron.price",
                        "",
                        "gui.shop.itemshop.buyable.armor.iron.lore",
                        "gui.shop.itemshop.buyable.armor.iron.lore.2"))
                .build();
    }
    public Gui gui() {
        return this.gui;
    }
}
