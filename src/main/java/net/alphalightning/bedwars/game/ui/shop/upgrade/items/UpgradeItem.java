package net.alphalightning.bedwars.game.ui.shop.upgrade.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
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
import java.util.Locale;

public class UpgradeItem extends AbstractItem {

    private final String translationKey;
    private final Material material;
    private final int itemAmount;
    private final List<String> loreLines;

    public UpgradeItem(String translationKey, Material material, int itemAmount, String... loreLines) {
        this.translationKey = translationKey;
        this.material = material;
        this.itemAmount = itemAmount;
        this.loreLines = List.of(loreLines);
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        final TranslatableComponent name = Component.translatable(this.translationKey);
        final List<Component> lore = translateLines(viewer, this.loreLines);
        final Locale locale = viewer.locale();

        return new ItemBuilder(this.material, this.itemAmount)
                .setName(GlobalTranslator.render(name, locale))
                .setLore(lore);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
    }

    private @NotNull List<Component> translateLines(Player player, @NotNull List<String> lines) {
        final List<Component> components = new ArrayList<>();
        for (String loreLineKey : lines) {
            if (loreLineKey.isBlank()) {
                components.add(Component.empty());
                continue;
            }
            TranslatableComponent component = Component.translatable(loreLineKey);
            components.add(GlobalTranslator.render(component, player.locale()));
        }
        return components;
    }
}
