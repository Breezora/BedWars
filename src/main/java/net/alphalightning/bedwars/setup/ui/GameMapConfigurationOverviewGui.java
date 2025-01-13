package net.alphalightning.bedwars.setup.ui;

import net.alphalightning.bedwars.setup.ui.item.ConfigureItemSpawnerGuiItem;
import net.alphalightning.bedwars.setup.ui.item.SelectTeamGuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

public class GameMapConfigurationOverviewGui {

    private final Player owner;
    private final Single gui;

    public GameMapConfigurationOverviewGui(Player owner) {
        this.owner = owner;
        this.gui = createGui();
    }

    private Single createGui() {
        return Window.single().setGui(Gui.normal()
                .setStructure(
                        ". . . . . . . . .",
                        ". . . a . b . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('.', Item.simple(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(Component.empty())
                        .hideTooltip(true))
                )
                .addIngredient('a', new SelectTeamGuiItem())
                .addIngredient('b', new ConfigureItemSpawnerGuiItem())
                .build()
        ).setTitle(Component.translatable("mapsetup.gui.overview.title"));
    }

    public void showGui() {
        gui.open(owner);
    }
}
