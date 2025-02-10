package net.alphalightning.bedwars.game.ui.item;

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

import java.util.ArrayList;
import java.util.List;

public class BuyableItem extends AbstractItem {

    private final Material itemMaterial;
    private final String itemNameKey;
    private final List<String> itemLore;
    private final int itemAmount;

    public BuyableItem(Material itemMaterial, String itemNameKey, int itemAmount, String... itemLore) {
        this.itemMaterial = itemMaterial;
        this.itemNameKey = itemNameKey;
        this.itemLore = List.of(itemLore);
        this.itemAmount = itemAmount;
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        final ItemBuilder builder = new ItemBuilder(itemMaterial)
                .setName(GlobalTranslator.render(Component.translatable(itemNameKey), viewer.locale()))
                .setAmount(itemAmount);

        List<Component> lore = new ArrayList<>();
        for (String s : itemLore) {
            if (s.isEmpty()) {
                lore.add(Component.empty());
                continue;
            }
            lore.add(GlobalTranslator.render(Component.translatable(s), viewer.locale()));
        }
        return builder.setLore(lore);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

    }
}
