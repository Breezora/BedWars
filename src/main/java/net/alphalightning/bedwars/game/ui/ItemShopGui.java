package net.alphalightning.bedwars.game.ui;

import net.alphalightning.bedwars.game.ui.item.*;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

import java.util.Arrays;
import java.util.List;

public class ItemShopGui {

    private final Single gui;

    public ItemShopGui() {
        this.gui = createGui();
    }

    Gui fastBuyGui = new FastBuyGui().gui();

    private Single createGui() {
        return Window.single()
                .setGui(PagedGui.guis()
                        .setStructure(
                                "a b c d e f g h a",
                                "x x x x x x x x x",
                                ". x x x x x x x .",
                                ". x x x x x x x .",
                                ". . . . . . . . ."
                        )
                        .addIngredient('a', new FastBuyItem())
                        .addIngredient('b', new BlocksItem())
                        .addIngredient('c', new CombatItem())
                        .addIngredient('d', new ArmorItem())
                        .addIngredient('e', new ToolsItem())
                        .addIngredient('f', new BowsItem())
                        .addIngredient('g', new PotionsItem())
                        .addIngredient('h', new ExtrasItem())
                        .setContent(List.of(fastBuyGui))
                        .build()
                )
                .setTitle(Component.translatable("gui.shop.itemshop.fastbuy.title"));
    }

    public void showGui(Player player) {
        gui.open(player);
    }

}
