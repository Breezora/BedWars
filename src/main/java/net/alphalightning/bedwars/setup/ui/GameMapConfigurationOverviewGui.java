package net.alphalightning.bedwars.setup.ui;

import net.alphalightning.bedwars.setup.ui.item.BackgroundGuiItem;
import net.alphalightning.bedwars.setup.ui.item.ConfigureItemSpawnerGuiItem;
import net.alphalightning.bedwars.setup.ui.item.SelectTeamGuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
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
                .addIngredient('.', new BackgroundGuiItem())
                .addIngredient('a', new SelectTeamGuiItem())
                .addIngredient('b', new ConfigureItemSpawnerGuiItem())
                .build()
        ).setTitle(Component.translatable("mapsetup.gui.overview.title"));
    }

    public void showGui() {
        gui.open(owner);
    }
}
