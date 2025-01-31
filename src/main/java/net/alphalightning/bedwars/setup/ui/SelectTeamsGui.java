package net.alphalightning.bedwars.setup.ui;

import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.ui.item.ReturnToOverviewGuiItem;
import net.alphalightning.bedwars.setup.ui.item.SaveConfigurationGuiItem;
import net.alphalightning.bedwars.setup.ui.item.TeamGuiItem;
import net.alphalightning.bedwars.setup.ui.item.TeamSelectionInfoGuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

import java.util.ArrayList;
import java.util.List;

public class SelectTeamsGui {

    private static final List<TeamGuiItem> SELECTED_TEAMS = new ArrayList<>();
    private static final List<TeamGuiItem> UNSELECTED_TEAMS = new ArrayList<>();

    private final Player owner;
    private final Single gui;

    private final GameMapSetup setup;
    private final GameMapConfigurationOverviewGui overviewGui;

    public SelectTeamsGui(Player owner, GameMapSetup setup, GameMapConfigurationOverviewGui overviewGui) {
        clearTeamSelections();

        this.owner = owner;
        this.setup = setup;
        this.overviewGui = overviewGui;
        this.gui = createGui();
    }

    private Single createGui() {
        return Window.single()
                .setGui(Gui.normal()
                        .setStructure(
                                "# + # # # # # - #",
                                ". . . . # a b c d",
                                ". . . . # e f g h",
                                ". . . . # i j k l",
                                ". . . . # m n o p",
                                "< # # # # # # # >"
                        )
                        .addIngredient('a', new TeamGuiItem(1, 0xffffff, owner))
                        .addIngredient('b', new TeamGuiItem(2, 0xaaaaaa, owner))
                        .addIngredient('c', new TeamGuiItem(3, 0x555555, owner))
                        .addIngredient('d', new TeamGuiItem(4, 0x000000, owner))
                        .addIngredient('e', new TeamGuiItem(5, 0x783d0c, owner))
                        .addIngredient('f', new TeamGuiItem(6, 0xff5555, owner))
                        .addIngredient('g', new TeamGuiItem(7, 0xff8800, owner))
                        .addIngredient('h', new TeamGuiItem(8, 0xffff55, owner))
                        .addIngredient('i', new TeamGuiItem(9, 0x55ff55, owner))
                        .addIngredient('j', new TeamGuiItem(10, 0x00aa00, owner))
                        .addIngredient('k', new TeamGuiItem(11, 0x00aaaa, owner))
                        .addIngredient('l', new TeamGuiItem(12, 0xa3e5ff, owner))
                        .addIngredient('m', new TeamGuiItem(13, 0x5555ff, owner))
                        .addIngredient('n', new TeamGuiItem(14, 0xaa00aa, owner))
                        .addIngredient('o', new TeamGuiItem(15, 0xff24fb, owner))
                        .addIngredient('p', new TeamGuiItem(16, 0xff6ef8, owner))
                        .addIngredient('+', new TeamSelectionInfoGuiItem(true)) // Team selection info items have to be added after the team gui items to display the correct amount of (un)selected teams
                        .addIngredient('-', new TeamSelectionInfoGuiItem(false))
                        .addIngredient('<', new ReturnToOverviewGuiItem(overviewGui))
                        .addIngredient('>', new SaveConfigurationGuiItem(setup))
                        .build()
                )
                .setTitle(Component.translatable("mapsetup.gui.select-teams.title"))
                .setCloseable(false);
    }

    public void showGui() {
        gui.open(owner);
    }

    public static List<TeamGuiItem> selectedTeams() {
        return SELECTED_TEAMS;
    }

    public static List<TeamGuiItem> unselectedTeams() {
        return UNSELECTED_TEAMS;
    }

    private void clearTeamSelections() {
        SELECTED_TEAMS.clear();
        UNSELECTED_TEAMS.clear();
    }

}
