package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.legacy.KitsItem;
import net.alphalightning.bedwars.game.ui.item.TabChangeItem;
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

    public ItemShopGui() {
        this.gui = createGui();
    }

    Gui fastBuyGui = new FastBuyGui().gui();
    Gui blocksGui = new BlocksGui().gui();
    Gui weaponsGui = new WeaponsGui().gui();
    Gui armorGui = new ArmorGui().gui();
    Gui toolsGui = new ToolsGui().gui();
    Gui bowsGui = new BowsGui().gui();
    Gui potionsGui = new PotionsGui().gui();
    Gui extrasGui = new ExtrasGui().gui();

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
                        .setTabs(List.of(fastBuyGui, blocksGui, weaponsGui, armorGui, toolsGui, bowsGui, potionsGui, extrasGui))
                        .build()
                )
                .setTitle(Component.translatable("gui.shop.itemshop.fastbuy.title"));

    }

    public void showGui(Player player) {
        gui.open(player);
    }

}
