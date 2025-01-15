package net.alphalightning.bedwars.setup.ui;

import net.alphalightning.bedwars.setup.ui.item.ReturnToOverviewGuiItem;
import net.alphalightning.bedwars.setup.ui.item.SaveConfigurationGuiItem;
import net.alphalightning.bedwars.setup.ui.item.TeamGuiItem;
import net.alphalightning.bedwars.setup.ui.item.TeamSelectionInfoGuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

public class SelectTeamsGui {

    private final Player owner;
    private final Single gui;

    public SelectTeamsGui(Player owner) {
        this.owner = owner;
        this.gui = createGui();
    }

    private Single createGui() {
        return Window.single().setGui(Gui.normal()
                .setStructure(
                        "# + # # # # # - #",
                        ". . . . # a b c d",
                        ". . . . # e f g h",
                        ". . . . # i j k l",
                        ". . . . # m n o p",
                        "< # # # # # # # >"
                )
                .addIngredient('+', new TeamSelectionInfoGuiItem(true))
                .addIngredient('-', new TeamSelectionInfoGuiItem(false))
                .addIngredient('<', new ReturnToOverviewGuiItem())
                .addIngredient('>', new SaveConfigurationGuiItem())
                .addIngredient('a', new TeamGuiItem(0xffffff, owner))
                .addIngredient('b', new TeamGuiItem(0xaaaaaa, owner))
                .addIngredient('c', new TeamGuiItem(0x555555, owner))
                .addIngredient('d', new TeamGuiItem(0x000000, owner))
                .addIngredient('e', new TeamGuiItem(0x783d0c, owner))
                .addIngredient('f', new TeamGuiItem(0xff5555, owner))
                .addIngredient('g', new TeamGuiItem(0xff8800, owner))
                .addIngredient('h', new TeamGuiItem(0xffff55, owner))
                .addIngredient('i', new TeamGuiItem(0x55ff55, owner))
                .addIngredient('j', new TeamGuiItem(0x00aa00, owner))
                .addIngredient('k', new TeamGuiItem(0x00aaaa, owner))
                .addIngredient('l', new TeamGuiItem(0xa3e5ff, owner))
                .addIngredient('m', new TeamGuiItem(0x5555ff, owner))
                .addIngredient('n', new TeamGuiItem(0xaa00aa, owner))
                .addIngredient('o', new TeamGuiItem(0xff24fb, owner))
                .addIngredient('p', new TeamGuiItem(0xff6ef8, owner))
                .build()
        ).setTitle(Component.translatable("mapsetup.gui.select-teams.title"));
    }

    public void showGui() {
        gui.open(owner);
    }

}
