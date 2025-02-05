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

public class WoolItem extends AbstractItem {

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("gui.shop.itemshop.buyable.wool.name");
        Component lore1 = Component.translatable("gui.shop.itemshop.buyable.wool.lore.tooltip");
        Component lore2 = Component.translatable("gui.shop.itemshop.buyable.wool.lore.tooltip.2");
        Component lore3 = Component.translatable("gui.shop.itemshop.buyable.wool.lore.tooltip.3");

        Component lore4 = Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-iron");
        return new ItemBuilder(Material.WHITE_WOOL)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .setLore(List.of(GlobalTranslator.render(lore1, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(lore2, viewer.locale()),
                        GlobalTranslator.render(lore3, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(lore4, viewer.locale())))
                .setAmount(16);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

    }
}
