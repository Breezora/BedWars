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

public class BowItem extends AbstractItem {

    private final int number;

    public BowItem(Integer number) {
        this.number = number;
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.empty();
        Component price = Component.empty();
        Component enough = Component.empty();

        switch (number) {
            case 1 -> {
                display = Component.translatable("gui.shop.itemshop.buyable.bow.name");
                price = Component.translatable("gui.shop.itemshop.buyable.bow.price");
                enough = Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-gold");
            }
            case 2 -> {
                display = Component.translatable("gui.shop.itemshop.buyable.bow.2.name");
                price = Component.translatable("gui.shop.itemshop.buyable.bow.2.price");
                enough = Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-gold");
            }
            case 3 -> {
                display = Component.translatable("gui.shop.itemshop.buyable.bow.3.name");
                price = Component.translatable("gui.shop.itemshop.buyable.bow.3.price");
                enough = Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-emerald");
            }
        }
        return new ItemBuilder(Material.BOW)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .setLore(List.of(GlobalTranslator.render(price, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(enough, viewer.locale())));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

    }
}
