package net.alphalightning.bedwars.game.ui.shop.item.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.PotionContents;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
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
import java.util.Map;
import java.util.Objects;

public class BuyablePotionItem extends AbstractItem {

    private final BedWarsPlugin plugin;

    private final String itemNameKey;
    private final List<String> itemLore;
    private final PotionEffectType potionEffectType;
    private final PotionType potionType;
    private final int duration;
    private final int amplifier;

    public BuyablePotionItem(BedWarsPlugin plugin, String itemNameKey, PotionType potionType, PotionEffectType potionEffectType, int duration, int amplifier, String... itemLore) {
        this.plugin = plugin;
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

        builder.setCustomName(GlobalTranslator.render(Component.translatable(itemNameKey), viewer.locale()))
                .set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().potion(potionType)
                        .addCustomEffect(new PotionEffect(potionEffectType, 20 * duration, amplifier)))
                .set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP);

        List<Component> lore = new ArrayList<>();
        for (String s : itemLore) {
            if (s.isEmpty()) {
                lore.add(Component.empty());
                continue;
            }
            lore.add(GlobalTranslator.render(Component.translatable(s), viewer.locale()));
        }
        String priceTag = getPriceTag(viewer);
        ItemStack currency = getCurrency(priceTag);
        int cost = extractAmount(priceTag);

        lore.add(Component.text(""));

        if (hasEnoughCurrency(viewer, currency, cost)) {
            lore.add(GlobalTranslator.render(Component.translatable("gui.shop.itemshop.buyable.enough"), viewer.locale()));
        } else {
            String notEnoughKey = "gui.shop.itemshop.buyable.lore.not-enough-emerald"; //as the potions can only be bought with emeralds
            lore.add(GlobalTranslator.render(Component.translatable(notEnoughKey), viewer.locale()));
        }
        return builder.setLore(lore);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {

        ItemStack currency = getCurrency(getPriceTag(player));
        int cost = extractAmount(getPriceTag(player));

        if (!hasEnoughCurrency(player, currency, cost)) {
            switch (currency.getType()) {
                case IRON_INGOT ->
                        player.sendMessage(Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-iron"));
                case GOLD_INGOT ->
                        player.sendMessage(Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-gold"));
                case EMERALD ->
                        player.sendMessage(Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-emerald"));
                case DIAMOND ->
                        player.sendMessage(Component.translatable("gui.shop.itemshop.buyable.lore.not-enough-diamond"));
            }
            player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.5F, 1.0F); //TODO: Change sound to hypixel sound
        } else {
            if (hasNotEnoughSpace(player)) {
                player.sendMessage(Component.translatable("player.inventory.full"));
                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.5F, 1.0F); //TODO: Change sound to hypixel sound
                return;
            }

            removeCurrency(player, currency, extractAmount(getPriceTag(player)));
            PotionType type = getPotionType(getItemProvider(player).get());

            if (type == PotionType.INVISIBILITY) {
                ItemStack invisibility = potion(Color.WHITE,
                        PotionEffectType.INVISIBILITY,
                        30, 0,
                        "Potion of Invisibility");
                player.getInventory().addItem(invisibility);
                return;
            }
            if (type == PotionType.LEAPING) {
                ItemStack jumpBoost = potion(Color.YELLOW,
                        PotionEffectType.JUMP_BOOST,
                        45, 4,
                        "Potion of Leaping");
                player.getInventory().addItem(jumpBoost);
                return;
            }
            if (type == PotionType.SWIFTNESS) {
                ItemStack swiftness = potion(Color.AQUA,
                        PotionEffectType.SPEED, 45, 1,
                        "Potion of Swiftness");
                player.getInventory().addItem(swiftness);
            }

        }
    }

    private ItemStack potion(Color potionColor, PotionEffectType type, int duration, int amplifier, String name) {

        PotionEffect potionEffect = new PotionEffect(type, duration * 20, amplifier);

        PotionContents contents = PotionContents.potionContents()
                .potion(PotionType.WATER)
                .customColor(potionColor)
                .addCustomEffect(potionEffect).build();

        return new ItemBuilder(Material.POTION)
                .set(DataComponentTypes.POTION_CONTENTS, contents)
                .set(DataComponentTypes.CUSTOM_NAME, Component.text(name).decoration(TextDecoration.ITALIC, false))
                .build();
    }

    private String getPriceTag(@NotNull Player viewer) {
        return Objects.requireNonNull(plugin.translator().getMiniMessageString(itemLore.getFirst(), viewer.locale()));
    }

    private ItemStack getCurrency(String toCheck) {
        Map<String, ItemStack> COLOR_TO_CURRENCY = Map.of(
                "white", new ItemStack(Material.IRON_INGOT),
                "gold", new ItemStack(Material.GOLD_INGOT),
                "dark_green", new ItemStack(Material.EMERALD),
                "aqua", new ItemStack(Material.DIAMOND)
        );

        String color = toCheck.split("<")[2].split(">")[0];
        return COLOR_TO_CURRENCY.get(color);
    }

    private int extractAmount(String input) {
        String[] parts = input.split(">");
        String amountPart = parts[2].trim().split(" ")[0];
        return Integer.parseInt(amountPart);
    }

    private boolean hasEnoughCurrency(Player player, ItemStack currency, int itemAmount) {
        int count = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.isSimilar(currency)) {
                count += item.getAmount();
                if (count >= itemAmount) return true;
            }
        }
        return false;
    }

    private void removeCurrency(Player player, ItemStack currency, int itemAmount) {
        ItemStack[] contents = player.getInventory().getContents();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null || !item.isSimilar(currency)) continue;

            int stackAmount = item.getAmount();

            if (stackAmount <= itemAmount) {
                contents[i] = null;
                itemAmount -= stackAmount;
            } else {
                item.setAmount(stackAmount - itemAmount);
                itemAmount = 0;
            }

            if (itemAmount <= 0) break;
        }

        player.getInventory().setContents(contents);
        player.updateInventory();
    }

    private boolean hasNotEnoughSpace(Player player) {
        ItemStack itemStack = getItemProvider(player).get();
        Inventory inventory = player.getInventory();

        int remainingAmount = itemStack.getAmount();
        int maxStackSize = itemStack.getMaxStackSize();

        if (inventory.firstEmpty() != -1) { // Check if we have minimum one empty slot
            remainingAmount -= maxStackSize;
            if (remainingAmount <= 0) {
                return false; // We have enough space to add the reward
            }
        }

        for (ItemStack slotItem : inventory.getContents()) {
            if (remainingAmount <= 0) {
                return false; // Since we have nothing more to add we have enough space
            }

            if (slotItem != null && slotItem.isSimilar(itemStack)) { // Check slots that have the same stack
                int spaceLeft = maxStackSize - slotItem.getAmount();
                remainingAmount -= Math.min(spaceLeft, remainingAmount);
            }
        }

        // If we have more than one item left to add we do not have enough space for the reward in that inventory
        return remainingAmount > 0;
    }

    public PotionType getPotionType(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() != Material.POTION) {
            throw new IllegalArgumentException("Item is not a potion.");
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (!(meta instanceof PotionMeta potionMeta)) {
            throw new IllegalStateException("ItemMeta is not PotionMeta.");
        }

        return potionMeta.getBasePotionType();
    }
}
