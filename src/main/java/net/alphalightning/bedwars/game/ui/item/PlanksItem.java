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

public class PlanksItem  extends AbstractItem {

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("gui.shop.itemshop.buyable.wood.name");

        Component price = Component.translatable("gui.shop.itemshop.buyable.wood.price");

        Component tooltip = Component.translatable("gui.shop.itemshop.buyable.wood.lore.tooltip");
        Component tooltip2 = Component.translatable("gui.shop.itemshop.buyable.wood.lore.tooltip.2");

        Component enough = Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-iron");
        return new ItemBuilder(Material.OAK_PLANKS)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .setLore(List.of(GlobalTranslator.render(price, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(tooltip, viewer.locale()),
                        GlobalTranslator.render(tooltip2, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(enough, viewer.locale())))
                .setAmount(16);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

    }
}
