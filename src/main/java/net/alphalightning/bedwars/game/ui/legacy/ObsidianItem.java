package net.alphalightning.bedwars.game.ui.legacy;

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

import java.util.List;

public class ObsidianItem extends AbstractItem {

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("gui.shop.itemshop.buyable.obsidian.name");

        Component price = Component.translatable("gui.shop.itemshop.buyable.obsidian.price");

        Component tooltip = Component.translatable("gui.shop.itemshop.buyable.obsidian.lore.tooltip");

        Component enough = Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-emerald");
        return new ItemBuilder(Material.OBSIDIAN)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .setLore(List.of(GlobalTranslator.render(price, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(tooltip, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(enough, viewer.locale())))
                .setAmount(4);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

    }
}
