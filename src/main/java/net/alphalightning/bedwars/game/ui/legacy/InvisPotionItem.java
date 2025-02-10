package net.alphalightning.bedwars.game.ui.legacy;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.PotionContents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.AbstractItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

import java.util.List;

public class InvisPotionItem extends AbstractItem {

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("gui.shop.itemshop.buyable.potion.invisibility.name");
        Component price = Component.translatable("gui.shop.itemshop.buyable.potion.invisibility.price");
        Component lore = Component.translatable("gui.shop.itemshop.buyable.potion.invisibility.lore");
        Component enough = Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-emerald");

        return new ItemBuilder(Material.POTION)
                .setCustomName(GlobalTranslator.render(display, viewer.locale()))
                .set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().potion(PotionType.INVISIBILITY)
                .addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 30, 0)))
                .set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP)
                .setLore(List.of(GlobalTranslator.render(price, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(lore, viewer.locale()),
                        Component.empty(),
                        GlobalTranslator.render(enough, viewer.locale())))
                ;

    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

    }
}
