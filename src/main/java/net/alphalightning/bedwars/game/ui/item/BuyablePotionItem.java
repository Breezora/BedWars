package net.alphalightning.bedwars.game.ui.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.PotionContents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Item;
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

import java.util.ArrayList;
import java.util.List;

public class BuyablePotionItem extends AbstractItem {

    private final Material itemMaterial;
    private final String itemNameKey;
    private final List<String> itemLore;
    private final PotionEffectType potionEffectType;
    private final PotionType potionType;
    private final int duration;
    private final int amplifier;
    public BuyablePotionItem(Material itemMaterial, String itemNameKey, PotionEffectType potionEffectType, PotionType potionType, int duration, int amplifier, String... itemLore) {
        this.itemMaterial = itemMaterial;
        this.itemNameKey = itemNameKey;
        this.potionEffectType = potionEffectType;
        this.potionType = potionType;
        this.duration = duration;
        this.amplifier = amplifier;
        this.itemLore = List.of(itemLore);
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        final ItemBuilder builder = new ItemBuilder(Material.POTION);

        if (itemMaterial != Material.POTION) {
            return new ItemBuilder(Material.BARRIER).setName("§cERROR! YOU MUST USE A POTION WHEN USING POTION BUILDER");
        } else {
            builder.setCustomName(GlobalTranslator.render(Component.translatable(itemNameKey), viewer.locale()))
                    .set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().potion(potionType)
                        .addCustomEffect(new PotionEffect(potionEffectType, 20 * duration, amplifier)))
                    .set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP);
        }
        List<Component> lore = new ArrayList<>();
        for (String s : itemLore) {
            if (s.isEmpty()) {
                lore.add(Component.empty());
                continue;
            }
            lore.add(GlobalTranslator.render(Component.translatable(s), viewer.locale()));
        }
        return builder.setLore(lore);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

    }
}
