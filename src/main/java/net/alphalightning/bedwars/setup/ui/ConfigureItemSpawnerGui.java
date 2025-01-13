package net.alphalightning.bedwars.setup.ui;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BundleContents;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

public class ConfigureItemSpawnerGui {

    private final Player owner;
    private final Single gui;

    public ConfigureItemSpawnerGui(Player owner) {
        this.owner = owner;
        this.gui = createGui();
    }

    private Single createGui() {
        return Window.single().setGui(Gui.normal()
                .setStructure(
                        ". . . . . . . . .",
                        ". . . a . b . . .",
                        "c . . . . . . . d"
                )
                .addIngredient('.', Item.simple(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(Component.empty())
                        .hideTooltip(true))
                )
                .addIngredient('a', Item.simple(new ItemBuilder(Material.EMERALD_BLOCK)))
                .addIngredient('b', Item.simple(new ItemBuilder(Material.DIAMOND_BLOCK)))
                .addIngredient('c', Item.simple(new ItemBuilder(Material.ARROW)))
                .addIngredient('d', Item.simple(new ItemBuilder(Material.GREEN_BUNDLE).set(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents().build())))
                .build()
        ).setTitle(Component.translatable("mapsetup.gui.overview.title"));
    }

    public void showGui() {
        gui.open(owner);
    }

}
