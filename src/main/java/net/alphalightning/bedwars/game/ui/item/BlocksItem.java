package net.alphalightning.bedwars.game.ui.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.item.*;

import java.util.Collections;

public class BlocksItem extends AbstractTabGuiBoundItem {
    private final TabGui gui = super.getGui();

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable(gui.getTab() == 0 ?
                "gui.shop.itemshop.blocks.name.selected" :
                "gui.shop.itemshop.blocks.name"
        );
        Component lore = Component.translatable("gui.shop.itemshop.lore");

        final ItemBuilder builder = new ItemBuilder(Material.TERRACOTTA)
                .setName(GlobalTranslator.render(display, viewer.locale()));

        if(gui.getTab() != 1) {
            builder.setLore(Collections.singletonList(GlobalTranslator.render(lore, viewer.locale())));
        }

        return builder;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        if (clickType != ClickType.LEFT) {
            return;
        }
        TabGui gui = super.getGui();
        gui.setTab(1);
    }
}
