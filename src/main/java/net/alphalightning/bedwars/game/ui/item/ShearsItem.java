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

import java.util.List;

public class ShearsItem extends AbstractItem {

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("gui.shop.itemshop.buyable.shears.name");

        Component price = Component.translatable("gui.shop.itemshop.buyable.shears.price");

        Component tooltip = Component.translatable("gui.shop.itemshop.buyable.shears.lore");
        Component tooltip2 = Component.translatable("gui.shop.itemshop.buyable.shears.lore.2");
        Component enough = Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-iron");
        return new ItemBuilder(Material.SHEARS)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .setLore(List.of(GlobalTranslator.render(price, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(tooltip, viewer.locale()),
                        GlobalTranslator.render(tooltip2, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(enough, viewer.locale())))
                ;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

    }
}
