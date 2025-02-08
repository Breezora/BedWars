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

public class CompactTowerItem extends AbstractItem {

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("gui.shop.itemshop.buyable.compacttower.name");
        Component price = Component.translatable("gui.shop.itemshop.buyable.compacttower.price");

        Component lore = Component.translatable("gui.shop.itemshop.buyable.compacttower.lore");

        Component enough = Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-gold");

        return new ItemBuilder(Material.CHEST)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .setLore(List.of(GlobalTranslator.render(price, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(lore, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(enough, viewer.locale())));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

    }
}
