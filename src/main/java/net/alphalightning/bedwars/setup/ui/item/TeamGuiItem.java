package net.alphalightning.bedwars.setup.ui.item;

import net.alphalightning.bedwars.feedback.Feedback;
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

import java.util.Comparator;
import java.util.List;

public class TeamGuiItem extends AbstractBoundItem {

    private final int weight;
    private final int color;
    private final Player viewer;

    private final List<TeamGuiItem> selectedTeams;
    private final List<TeamGuiItem> unselectedTeams;

    private final int[] selectedSlots = { // The slots where selected team items are rendered; see SelectTeamsGui --> structure
            9, 10, 11, 12,
            18, 19, 20, 21,
            27, 28, 29, 30,
            36, 37, 38, 39
    };
    private final int[] unselectedSlots = { // The slots where non-selected team items are rendered; see SelectTeamsGui --> structure
            14, 15, 16, 17,
            23, 24, 25, 26,
            32, 33, 34, 35,
            41, 42, 43, 44
    };

    public TeamGuiItem(int weight, int color, Player viewer) {
        this.weight = weight;
        this.color = color;
        this.viewer = viewer;

        this.selectedTeams = SelectTeamsGui.selectedTeams();
        this.unselectedTeams = SelectTeamsGui.unselectedTeams();

        unselectedTeams.add(this);
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        return createItemBuilderFromColor();
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        if (click.getClickType() != ClickType.LEFT) {
            return; // We only want to allow a single left click
        }

        Gui gui = super.getGui();

        updateTeamSelection(gui);
        refresh(gui, click);
        Feedback.click(click.getPlayer());
    }

    public int weight() {
        return weight; // The weight that is used to sort the items
    }

    public int color() {
        return color;
    }

    private @NotNull ItemBuilder createItemBuilderFromColor() {
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

    private void updateTeamSelection(Gui gui) {
        if (unselectedTeams.contains(this)) { // Select a team
            unselectedTeams.remove(this);
            selectedTeams.add(this);

        } else { // Unselect a team
            selectedTeams.remove(this);
            unselectedTeams.add(this);
        }

        updateTeamSelectionInfoItems(gui);
    }

    private void updateTeamSelectionInfoItems(Gui gui) {
        int[] slots = {1, 7}; // The slots where team info items are placed into; see SelectTeamsGui --> structure

        for (int slot : slots) {
            if (!(gui.getItem(slot) instanceof TeamSelectionInfoGuiItem item)) {
                continue; // We don't care about items that are not info items
            }
            item.notifyWindows(); // Update the item
        }
    }

    private void refresh(Gui gui, Click click) {
        if (selectedTeams.contains(this)) { // Item was selected --> move from right to left
            sortByWeight(selectedTeams);
            transfer(gui, selectedSlots, selectedTeams);
            moveUp(gui, click, unselectedSlots, unselectedTeams);
            return;
        }

        // Move items from left to right
        sortByWeight(unselectedTeams);
        transfer(gui, unselectedSlots, unselectedTeams);
        moveUp(gui, click, selectedSlots, selectedTeams);
    }

    private void transfer(Gui gui, int[] targetSlots, List<TeamGuiItem> group) {
        int size = Math.min(targetSlots.length, group.size()); // Make sure the slot count is sufficient to render the group of items

        // Set weighted items
        for (int i = 0; i < size; i++) {
            gui.setItem(targetSlots[i], group.get(i));
        }

        // Clear overflowing slots
        for (int i = size; i < targetSlots.length; i++) {
            gui.setItem(targetSlots[i], new BackgroundGuiItem(false));
        }

        gui.notifyWindows(); // Refresh the ui
    }

    private void moveUp(Gui gui, Click click, int[] targetSlots, List<TeamGuiItem> group) {
        int index = findIndex(targetSlots, click.getSlot());
        int lowest = findLastUsedSlotIndex(gui, targetSlots, index);

        // Safety check; this exception should never be thrown
        if (index == -1 || lowest == -1) {
            throw new IllegalStateException("An index cannot be negative");
        }

        for (int i = index; i < lowest; i++) { // Loop through the range of the clicked slot and the last team item
            if (i >= group.size()) {
                gui.setItem(targetSlots[i], new BackgroundGuiItem(false)); // Place a background item at the new last slot

            } else {
                gui.setItem(targetSlots[i], group.get(i)); // Move the items
            }
        }
        gui.notifyWindows(); // Refresh the ui
    }

    private int findLastUsedSlotIndex(Gui gui, int[] array, int lowest) {
        for (int i = array.length; i > lowest; i--) {
            if (gui.getItem(i) instanceof BackgroundGuiItem) {
                continue; // We are only interested in slots that have team items
            }
            return i; // Return the last slot where a team item is placed in
        }
        return -1;
    }

    private int findIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != value) { // Check if value is value in array
                continue;
            }
            return i; // Return array index where value was found
        }
        return -1;
    }

    private void sortByWeight(List<TeamGuiItem> list) {
        list.sort(Comparator.comparingInt(TeamGuiItem::weight)); // Sort list by its weight
    }

}
