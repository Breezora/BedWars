package net.alphalightning.bedwars.game.ui.shop.upgrade.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.Click;

public class QueueItem extends UpgradeItem {

    public QueueItem(String translationKey, int itemAmount, String... loreLines) {
        super(translationKey, Material.LIGHT_GRAY_STAINED_GLASS, itemAmount, loreLines);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        // Do nothing. This is an information item
    }
}
