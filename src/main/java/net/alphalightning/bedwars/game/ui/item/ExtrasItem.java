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

public class ExtrasItem extends AbstractItem {
    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("gui.shop.itemshop.extras.name");
        Component lore = Component.translatable("gui.shop.itemshop.lore");

        return new ItemBuilder(Material.TNT)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .setLore(List.of(lore));
    }

    @Override
    public void handleClick(ClickType clickType, Player player, Click click) {

    }
}
