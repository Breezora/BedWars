package net.alphalightning.bedwars.setup.ui.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class SelectSpawnerGuiItem extends PageItem {

    private final NamespacedKey key = new NamespacedKey("bedwars", "setup-stage");
    private final Player player;

    public SelectSpawnerGuiItem(Player player) {
        super(true);
        this.player = player;
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> gui) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        int stage = container.getOrDefault(key, PersistentDataType.INTEGER, 0);

        Component name = (stage == 8 || stage == 9) ?
                Component.translatable("mapsetup.gui.overview.select-spawner.name") :
                Component.translatable("mapsetup.gui.overview.select-spawner.name.locked");

        return new ItemBuilder(Material.RED_BED)
                .setDisplayName(new AdventureComponentWrapper(name))
                .addLoreLines(
                        new AdventureComponentWrapper(Component.empty()),
                        new AdventureComponentWrapper(Component.translatable("mapsetup.gui.overview.select-spawner.lore")),
                        new AdventureComponentWrapper(Component.empty())
                );
    }
}
