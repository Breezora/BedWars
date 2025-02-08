package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.*;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.Markers;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.item.AbstractTabGuiBoundItem;
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

    AbstractTabGuiBoundItem fastBuyItem = new FastBuyItem();
    AbstractTabGuiBoundItem blocksItem = new BlocksItem();

    private Single createGui() {

        fastBuyItem.bind(fastBuyGui);
        blocksItem.bind(blocksGui);

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
                        .addIngredient('0', new FastBuyItem())
                        .addIngredient('1', new BlocksItem())
                        .addIngredient('2', new CombatItem())
                        .addIngredient('3', new ArmorItem())
                        .addIngredient('4', new ToolsItem())
                        .addIngredient('5', new BowsItem())
                        .addIngredient('6', new PotionsItem())
                        .addIngredient('7', new ExtrasItem())
                        .addIngredient('8', new KitsItem())
                        .setTabs(List.of(fastBuyGui, blocksGui))
                        .build()
                )
                .setTitle(Component.translatable("gui.shop.itemshop.fastbuy.title"));

    }

    public void showGui(Player player) {
        gui.open(player);
    }

}
