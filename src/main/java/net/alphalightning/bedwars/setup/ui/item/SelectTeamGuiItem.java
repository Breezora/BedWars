package net.alphalightning.bedwars.setup.ui.item;

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

public class SelectTeamGuiItem extends AbstractItem {

    private final NamespacedKey key = new NamespacedKey("bedwars", "stage");
    private final Player owner;

    public SelectTeamGuiItem(Player owner) {
        this.owner = owner;
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        PersistentDataContainer container = owner.getPersistentDataContainer();
        int stage = container.getOrDefault(key, PersistentDataType.INTEGER, 0);

        return new ItemBuilder(Material.RED_BED).setName("Test");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        //TODO: Run click logic (open new gui)
    }
}
