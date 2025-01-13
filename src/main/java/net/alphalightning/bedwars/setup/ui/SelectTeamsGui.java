package net.alphalightning.bedwars.setup.ui;

import net.alphalightning.bedwars.setup.ui.item.*;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public class SelectTeamsGui {

    private final Player owner;
    private final Window.Builder.Normal.Single gui;

    public SelectTeamsGui(Player owner) {
        this.owner = owner;
        this.gui = createGui();
    }

    private Window.Builder.Normal.Single createGui() {
        return Window.single().setGui(Gui.normal()
                .setStructure(
                        "# + # # # # # - #",
                        ". . . . # . . . .",
                        ". . . . # . . . .",
                        ". . . . # . . . .",
                        ". . . . # . . . .",
                        "< # # # # # # # >"
                )
                .addIngredient('.', new BackgroundGuiItem())
                .addIngredient('#', new DividerGuiItem())
                .addIngredient('+', new TeamSelectionInfoGuiItem(true))
                .addIngredient('-', new TeamSelectionInfoGuiItem(false))
                .addIngredient('<', new ReturnToOverviewGuiItem())
                .addIngredient('>', new SaveConfigurationGuiItem())
                .build()
        ).setTitle(Component.translatable("mapsetup.gui.select-teams.title"));
    }

    public void showGui() {
        gui.open(owner);
    }

}
