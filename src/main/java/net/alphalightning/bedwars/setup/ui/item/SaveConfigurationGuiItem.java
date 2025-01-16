package net.alphalightning.bedwars.setup.ui.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.alphalightning.bedwars.setup.ui.GameMapConfigurationOverviewGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.AbstractItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

public class SaveConfigurationGuiItem extends AbstractItem {

    private final NamespacedKey key = new NamespacedKey("bedwars", "stage");
    private final PersistentDataContainer container;

    public SaveConfigurationGuiItem(PersistentDataContainer container) {
        this.container = container;
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("mapsetup.gui.save-configuration");

        return new ItemBuilder(Material.GREEN_BUNDLE)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .unset(DataComponentTypes.BUNDLE_CONTENTS); // Disable display of bundles' content
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        int stage = container.getOrDefault(key, PersistentDataType.INTEGER, 0);

        new GameMapConfigurationOverviewGui(player).showGui();
        container.set(key, PersistentDataType.INTEGER, ++stage);

        //TODO: Setup nehmen und neue stage starten
    }

}
