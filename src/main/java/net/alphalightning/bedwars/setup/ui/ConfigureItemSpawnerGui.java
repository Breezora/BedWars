package net.alphalightning.bedwars.setup.ui;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.alphalightning.bedwars.setup.ui.item.EmeraldSpawnerGuiItem;
import net.alphalightning.bedwars.setup.ui.item.ReturnToOverviewGuiItem;
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
                .addIngredient('a', new EmeraldSpawnerGuiItem())
                .addIngredient('b', Item.simple(new ItemBuilder(Material.DIAMOND_BLOCK)))
                .addIngredient('c', new ReturnToOverviewGuiItem())
                .addIngredient('d', Item.simple(new ItemBuilder(Material.GREEN_BUNDLE).unset(DataComponentTypes.BUNDLE_CONTENTS)))
                .build()
        ).setTitle(Component.translatable("mapsetup.gui.configure-spawner.title"));
    }

    public void showGui() {
        gui.open(owner);
    }

}
