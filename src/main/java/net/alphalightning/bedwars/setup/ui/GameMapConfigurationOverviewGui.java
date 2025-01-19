package net.alphalightning.bedwars.setup.ui;

import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.ui.item.ConfigureItemSpawnerGuiItem;
import net.alphalightning.bedwars.setup.ui.item.SelectTeamGuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

public class GameMapConfigurationOverviewGui {

    private final Player owner;
    private final GameMapSetup setup;

    private final Single gui;

    public GameMapConfigurationOverviewGui(Player owner, GameMapSetup setup) {
        this.owner = owner;
        this.setup = setup;
        this.gui = createGui(owner.getPersistentDataContainer());
    }

    private Single createGui(PersistentDataContainer container) {
        return Window.single()
                .setGui(Gui.normal()
                        .setStructure(
                                ". . . . . . . . .",
                                ". . . a . b . . .",
                                ". . . . . . . . ."
                        )
                        .addIngredient('a', new SelectTeamGuiItem(setup))
                        .addIngredient('b', new ConfigureItemSpawnerGuiItem(container))
                        .build()
                )
                .setTitle(Component.translatable("mapsetup.gui.overview.title"))
                .setCloseable(false);
    }

    public void showGui() {
        gui.open(owner);
    }
}
