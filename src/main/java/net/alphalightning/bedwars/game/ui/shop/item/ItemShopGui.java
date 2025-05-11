package net.alphalightning.bedwars.game.ui.shop.item;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.ui.shop.item.items.TabChangeItem;
import net.alphalightning.bedwars.game.ui.shop.item.legacy.KitsItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.Markers;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

import java.util.List;

public class ItemShopGui {

    private final Single gui;

    Gui fastBuyGui;
    Gui blocksGui;
    Gui weaponsGui;
    Gui armorGui;
    Gui toolsGui;
    Gui bowsGui;
    Gui potionsGui;
    Gui extrasGui;

    public ItemShopGui(BedWarsPlugin plugin) {
        this.gui = createGui();

        fastBuyGui = new FastBuyGui(plugin).gui();
        blocksGui = new BlocksGui(plugin).gui();
        weaponsGui = new WeaponsGui(plugin).gui();
        armorGui = new ArmorGui(plugin).gui();
        toolsGui = new ToolsGui(plugin).gui();
        bowsGui = new BowsGui(plugin).gui();
        potionsGui = new PotionsGui().gui();
        extrasGui = new ExtrasGui(plugin).gui();

    }

    private Single createGui() {
        return Window.single()
                .setGui(TabGui.normal()
                        .setStructure(
                                "0 1 2 3 4 5 6 7 8",
                                "x x x x x x x x x",
                                "x x x x x x x x x",
                                "x x x x x x x x x",
                                ". . . . . . . . ."
                        )
                        .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                        .addIngredient('0', new TabChangeItem(Material.NETHER_STAR, "gui.shop.itemshop.fastbuy.name", 0))
                        .addIngredient('1', new TabChangeItem(Material.TERRACOTTA, "gui.shop.itemshop.blocks.name", 1))
                        .addIngredient('2', new TabChangeItem(Material.GOLDEN_SWORD, "gui.shop.itemshop.combat.name", 2))
                        .addIngredient('3', new TabChangeItem(Material.CHAINMAIL_BOOTS, "gui.shop.itemshop.armor.name", 3))
                        .addIngredient('4', new TabChangeItem(Material.STONE_PICKAXE, "gui.shop.itemshop.tools.name", 4))
                        .addIngredient('5', new TabChangeItem(Material.BOW, "gui.shop.itemshop.bows.name", 5))
                        .addIngredient('6', new TabChangeItem(Material.BREWING_STAND, "gui.shop.itemshop.potions.name", 6))
                        .addIngredient('7', new TabChangeItem(Material.TNT, "gui.shop.itemshop.extras.name", 7))
                        .addIngredient('8', new KitsItem())
                        .setTabs(List.of(this.fastBuyGui, this.blocksGui, this.weaponsGui, this.armorGui, this.toolsGui, this.bowsGui, this.potionsGui, this.extrasGui))
                        .build()
                )
                .setTitle(Component.translatable("gui.shop.itemshop.fastbuy.title"));

    }

    public void showGui(Player player) {
        this.gui.open(player);
    }

}
