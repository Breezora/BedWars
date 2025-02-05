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

public class WoodenPickaxeItem extends AbstractItem {

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("gui.shop.itemshop.buyable.woodpickaxe.name");

        Component price = Component.translatable("gui.shop.itemshop.buyable.woodpickaxe.price");

        Component level = Component.translatable("gui.shop.itemshop.upgradable.level1");

        Component upgrade = Component.translatable("gui.shop.itemshop.buyable.lore.upgradable");
        Component upgrade2 = Component.translatable("gui.shop.itemshop.buyable.lore.upgradable2");

        Component permrespawn = Component.translatable("gui.shop.itemshop.buyable.lore.permrespawn");
        Component permrespawn2 = Component.translatable("gui.shop.itemshop.buyable.lore.permrespawn2");
        Component permrespawn3 = Component.translatable("gui.shop.itemshop.buyable.lore.permrespawn3");

        Component enough = Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-iron");
        return new ItemBuilder(Material.WOODEN_PICKAXE)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .setLore(List.of(GlobalTranslator.render(price, viewer.locale()),
                        GlobalTranslator.render(level, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(upgrade, viewer.locale()),
                        GlobalTranslator.render(upgrade2, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(permrespawn, viewer.locale()),
                        GlobalTranslator.render(permrespawn2, viewer.locale()),
                        GlobalTranslator.render(permrespawn3, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(enough, viewer.locale())));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

    }
}
