package net.alphalightning.bedwars.game.ui.shop.item.items;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.AbstractItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BuyableItem extends AbstractItem {

    private final BedWarsPlugin plugin;

    private final Material itemMaterial;
    private final String itemNameKey;
    private final List<String> itemLore;
    private final int itemAmount;

    public BuyableItem(BedWarsPlugin plugin, Material itemMaterial, String itemNameKey, int itemAmount, String... itemLore) {
        this.plugin = plugin;
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
    private String getPriceTag(@NotNull Player viewer) {
        return Objects.requireNonNull(plugin.translator().getMiniMessageString(itemLore.getFirst(), viewer.locale()));
    }

    private String getCurrency(String toCheck) {
        Map<String, String> COLOR_TO_CURRENCY = Map.of(
                "white", "IRON",
                "gold", "GOLD",
                "dark_green", "EMERALD",
                "aqua", "DIAMOND"
        );

        String color = toCheck.split("<")[2].split(">")[0];
        return COLOR_TO_CURRENCY.get(color);
    }

    private int extractAmount(String input) {
        String[] parts = input.split(">");
        String amountPart = parts[2].trim().split(" ")[0];
        return Integer.parseInt(amountPart);
    }
    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        System.out.println("Dieses Item kostet: " + getCurrency(getPriceTag(player)) + " " + extractAmount(getPriceTag(player)));
    }

    private boolean hasNotEnoughSpace(Player player) {
        ItemStack itemStack = getItemProvider(player).get();
        Inventory inventory = player.getInventory();

        int remainingAmount = itemStack.getAmount();
        int maxStackSize = itemStack.getMaxStackSize();

        if (inventory.firstEmpty() != -1) { // Check if we have minimum one empty slot
            remainingAmount -= maxStackSize;
            if (remainingAmount <= 0) {
                return false; // We have enough space to add the reward
            }
        }

        for (ItemStack slotItem : inventory.getContents()) {
            if (remainingAmount <= 0) {
                return false; // Since we have nothing more to add we have enough space
            }

            if (slotItem != null && slotItem.isSimilar(itemStack)) { // Check slots that have the same stack
                int spaceLeft = maxStackSize - slotItem.getAmount();
                remainingAmount -= Math.min(spaceLeft, remainingAmount);
            }
        }

        // If we have more than one item left to add we do not have enough space for the reward in that inventory
        return remainingAmount > 0;
    }
}
