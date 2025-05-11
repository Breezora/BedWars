package net.alphalightning.bedwars.game.ui.shop.item.items;

import net.alphalightning.bedwars.BedWarsPlugin;
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
}
