package net.alphalightning.bedwars.setup.ui.item;

import net.alphalightning.bedwars.setup.ui.SelectTeamsGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.AbstractBoundItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

import java.util.List;

public class TeamGuiItem extends AbstractBoundItem {

    private final int color;
    private final Player viewer;

    private final List<TeamGuiItem> selectedTeams;
    private final List<TeamGuiItem> unselectedTeams;

    private final int[] selectedSlots = {
            9, 10, 11, 12,
            18, 19, 20, 21,
            27, 28, 29, 30,
            36, 37, 38, 39
    };
    private final int[] unselectedSlots = {
            14, 15, 16, 17,
            23, 24, 25, 26,
            32, 33, 34, 35,
            41, 42, 43, 44
    };

    public TeamGuiItem(int color, Player viewer) {
        this.color = color;
        this.viewer = viewer;

        this.selectedTeams = SelectTeamsGui.selectedTeams();
        this.unselectedTeams = SelectTeamsGui.unselectedTeams();

        unselectedTeams.add(this);
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        return fromColor().clone();
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        if (click.getClickType() != ClickType.LEFT) {
            return;
        }

        updateTeamSelection();
        refresh(click);

        notifyWindows();
    }

    private @NotNull ItemBuilder fromColor() {
        ItemBuilder builder = new ItemBuilder(Material.BARRIER); // Default empty slot if no valid hex-int was supplied
        Component display = Component.empty();

        switch (color) { // Colors are default hex encoded int values
            case 0xffffff -> {
                display = Component.translatable("mapsetup.gui.select-teams.white");
                builder.setMaterial(Material.WHITE_WOOL);
            }
            case 0xaaaaaa -> {
                display = Component.translatable("mapsetup.gui.select-teams.light-gray");
                builder.setMaterial(Material.LIGHT_GRAY_WOOL);
            }
            case 0x555555 -> {
                display = Component.translatable("mapsetup.gui.select-teams.dark-gray");
                builder.setMaterial(Material.GRAY_WOOL);
            }
            case 0x000000 -> {
                display = Component.translatable("mapsetup.gui.select-teams.black");
                builder.setMaterial(Material.BLACK_WOOL);
            }
            case 0x783d0c -> {
                display = Component.translatable("mapsetup.gui.select-teams.brown");
                builder.setMaterial(Material.BROWN_WOOL);
            }
            case 0xff5555 -> {
                display = Component.translatable("mapsetup.gui.select-teams.red");
                builder.setMaterial(Material.RED_WOOL);
            }
            case 0xff8800 -> {
                display = Component.translatable("mapsetup.gui.select-teams.orange");
                builder.setMaterial(Material.ORANGE_WOOL);
            }
            case 0xffff55 -> {
                display = Component.translatable("mapsetup.gui.select-teams.yellow");
                builder.setMaterial(Material.YELLOW_WOOL);
            }
            case 0x55ff55 -> {
                display = Component.translatable("mapsetup.gui.select-teams.light-green");
                builder.setMaterial(Material.LIME_WOOL);
            }
            case 0x00aa00 -> {
                display = Component.translatable("mapsetup.gui.select-teams.green");
                builder.setMaterial(Material.GREEN_WOOL);
            }
            case 0x00aaaa -> {
                display = Component.translatable("mapsetup.gui.select-teams.cyan");
                builder.setMaterial(Material.CYAN_WOOL);
            }
            case 0xa3e5ff -> {
                display = Component.translatable("mapsetup.gui.select-teams.light-blue");
                builder.setMaterial(Material.LIGHT_BLUE_WOOL);
            }
            case 0x5555ff -> {
                display = Component.translatable("mapsetup.gui.select-teams.blue");
                builder.setMaterial(Material.BLUE_WOOL);
            }
            case 0xaa00aa -> {
                display = Component.translatable("mapsetup.gui.select-teams.purple");
                builder.setMaterial(Material.PURPLE_WOOL);
            }
            case 0xff24fb -> {
                display = Component.translatable("mapsetup.gui.select-teams.magenta");
                builder.setMaterial(Material.MAGENTA_WOOL);
            }
            case 0xff6ef8 -> {
                display = Component.translatable("mapsetup.gui.select-teams.pink");
                builder.setMaterial(Material.PINK_WOOL);
            }
        }

        return builder.setName(GlobalTranslator.render(display, viewer.locale()));
    }

    private void updateTeamSelection() {
        if (unselectedTeams.contains(this)) {
            unselectedTeams.remove(this);
            selectedTeams.add(this);

        } else {
            selectedTeams.remove(this);
            unselectedTeams.add(this);
        }

        updateTeamSelectionInfoItems(super.getGui());
    }

    private void updateTeamSelectionInfoItems(Gui gui) {
        int[] slots = {1, 7};

        for (int slot : slots) {
            if (!(gui.getItem(slot) instanceof TeamSelectionInfoGuiItem item)) {
                continue;
            }
            item.notifyWindows();
        }
    }

    private void refresh(Click click) {
        Gui gui = super.getGui();

        transfer(gui);
        moveUp(gui, click);
    }

    private void transfer(Gui gui) {
        for (int slot : selectedSlots) {
            if (!(gui.getItem(slot) instanceof BackgroundGuiItem)) {
                continue;
            }

            gui.setItem(slot, this);
            gui.notifyWindows();
            return;
        }
    }

    private void moveUp(Gui gui, Click click) {
        int index = findIndex(selectedSlots, click.getSlot());
        int lowest = findLastUsedSlotIndex(gui, selectedSlots, index);

        for (int i = index; i < lowest; i++) {
            if (i >= selectedTeams.size()) {
                gui.setItem(selectedSlots[i], new BackgroundGuiItem());

            } else {
                gui.setItem(selectedSlots[i], selectedTeams.get(i));
            }
        }
        gui.notifyWindows();
    }

    private int findLastUsedSlotIndex(Gui gui, int[] array, int lowest) {
        for (int i = array.length; i > lowest; i--) {
            if (gui.getItem(i) instanceof BackgroundGuiItem) {
                continue;
            }
            return i;
        }
        return -1;
    }

    private int findIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != value) {
                continue;
            }
            return i;
        }
        return -1;
    }

    enum Direction {
        SELECTION, DESELECTION
    }
}
