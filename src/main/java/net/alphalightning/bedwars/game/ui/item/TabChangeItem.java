package net.alphalightning.bedwars.game.ui.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.item.AbstractTabGuiBoundItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

import java.util.Collections;

public class TabChangeItem extends AbstractTabGuiBoundItem {

    private final Material itemMaterial;
    private final String itemNameKey;
    private final Integer boundToTab;

    public TabChangeItem(Material itemMaterial, String itemNameKey, Integer boundToTab) {
        this.itemMaterial = itemMaterial;
        this.itemNameKey = itemNameKey;
        this.boundToTab = boundToTab;
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        TabGui gui = super.getGui();

        Component display = Component.translatable(gui.getTab() == boundToTab ?
                itemNameKey + ".selected" :
                itemNameKey
        );
        Component lore = Component.translatable("gui.shop.itemshop.lore");

        final ItemBuilder builder = new ItemBuilder(itemMaterial)
                .setName(GlobalTranslator.render(display, viewer.locale()));

        if(gui.getTab() != boundToTab) {
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
        gui.setTab(boundToTab);
    }
}
