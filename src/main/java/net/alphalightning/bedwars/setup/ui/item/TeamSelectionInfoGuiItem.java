package net.alphalightning.bedwars.setup.ui.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.AbstractItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

public class TeamSelectionInfoGuiItem extends AbstractItem {

    private final boolean selected;

    public TeamSelectionInfoGuiItem(boolean selected) {
        this.selected = selected;
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable(selected ?
                "mapsetup.gui.select-teams.selection-info.selected" :
                "mapsetup.gui.select-teams.selection-info.unselected"
        );

        return new ItemBuilder(Material.NAME_TAG)
                .setName(GlobalTranslator.render(display, viewer.locale())); // Disable display of bundles' content
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        // No interaction logic is needed
    }

}
