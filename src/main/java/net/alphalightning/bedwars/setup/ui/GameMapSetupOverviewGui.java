package net.alphalightning.bedwars.setup.ui;

import net.alphalightning.bedwars.setup.ui.item.SelectTeamGuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

public final class GameMapSetupOverviewGui {

    private final Player owner;
    private final Single gui;

    public GameMapSetupOverviewGui(Player owner) {
        this.owner = owner;
        this.gui = createGui();
    }

    private Single createGui() {
        return Window.single().setGui(PagedGui.guis()
                .setStructure(
                        ". . . . . . . . .",
                        ". . . a . b . . .",
                        ". . . . . . . . ."
                )
                .addIngredient('a', new SelectTeamGuiItem(owner))
                .addIngredient('b', new SimpleItem(new ItemBuilder(Material.TRIAL_SPAWNER)))
                .build()
        ).setTitle(new AdventureComponentWrapper(Component.translatable("mapsetup.gui.overview.title")));
    }

    public void openGui() {
        gui.open(owner);
    }

}
