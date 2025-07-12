package net.alphalightning.bedwars.game.ui.shop.item.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import io.papermc.paper.datacomponent.item.Unbreakable;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.state.states.InGameState;
import net.alphalightning.bedwars.game.team.Team;
import net.alphalightning.bedwars.util.PlayerUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.AbstractItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BuyableItem extends AbstractItem {

    private final BedWarsPlugin plugin;

    private final Material itemMaterial;
    private final String itemNameKey;
    private final List<String> itemLore;
    private final int itemAmount;

    private final List<Team> teams;

    private static final Map<Material, Integer> ARMOR_RANKING = Map.of(
            Material.CHAINMAIL_BOOTS, 1,
            Material.IRON_BOOTS, 2,
            Material.DIAMOND_BOOTS, 3
    );

    public BuyableItem(BedWarsPlugin plugin, Material itemMaterial, String itemNameKey, int itemAmount, String... itemLore) {
        this.plugin = plugin;
        this.itemMaterial = itemMaterial;
        this.itemNameKey = itemNameKey;
        this.itemLore = List.of(itemLore);
        this.itemAmount = itemAmount;
        this.teams = InGameState.teamsList();
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        final ItemBuilder builder = new ItemBuilder(itemMaterial)
                .setName(GlobalTranslator.render(Component.translatable(itemNameKey), viewer.locale()))
                .setAmount(itemAmount);

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
        ItemStack boughtItem = getItemProvider(player).get().clone();
        Material type = boughtItem.getType();

        String priceTag = getPriceTag(player);
        int cost = extractAmount(priceTag);
        int itemAmount = getItemProvider(player).get().getAmount();
        String currencyString = getCurrencyString(priceTag);
        ItemStack currency = getCurrency(priceTag);

        for (Team team : teams) {
            if (!team.players().contains(player)) continue;

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
                removeCurrency(player, currency, cost);
                //TODO: add sound to confirm the buying

                //Handle buying of colored items
                if (type.name().endsWith("_WOOL")) {
                    buyColoredItem(ColoredItem.WOOL, team, player);
                    return;
                } else if (type == Material.TERRACOTTA) {
                    buyColoredItem(ColoredItem.TERRACOTTA, team, player);
                    return;
                } else if (type == Material.GLASS) {
                    buyColoredItem(ColoredItem.GLASS, team, player);
                    return;
                }

                //Handle buying of Enchanted Items
                else if (type == Material.STICK) {
                    handleEnchantmentPurchase(player, 1, type, Enchantment.KNOCKBACK, 1);
                    return;
                } else if (type == Material.BOW) {
                    if (currencyString.equals("emerald")) {
                        Map<Enchantment, Integer> enchantmentMap = Map.of(
                                Enchantment.POWER, 1,
                                Enchantment.PUNCH, 1
                        );
                        handleEnchantmentPurchase(player, 1, type, enchantmentMap);
                        return;
                    }

                    if (cost == 20) {
                        handleEnchantmentPurchase(player, 1, type, Enchantment.POWER, 1);
                        return;
                    }
                }
                //Handle buying of Swords
                else if (type.name().endsWith("_SWORD")) {
                    handleSwordPurchase(player, type);
                    return;
                }
                //Handle buying of shears
                else if (type == Material.SHEARS) {
                    ItemStack bought = new ItemBuilder(type)
                            .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable()).build();
                    player.getInventory().addItem(bought);
                    return;
                }
                //Handle buying of Armor. This implies, that the price can be changed, but not the currency of the armor.
                else if (type.name().endsWith("_BOOTS")) {
                    handleArmorPurchase(player, type, currency, cost);
                    return;
                }
                ItemBuilder builder = new ItemBuilder(type)
                        .setAmount(itemAmount);

                ItemStack bought = builder.build();
                player.getInventory().addItem(bought);
            }
        }
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

    private void buyColoredItem(ColoredItem item, Team team, Player player) {
        switch (item) {
            case WOOL -> {
                ItemBuilder builder = new ItemBuilder(Objects.requireNonNull(
                        Material.getMaterial(PlayerUtil.materialString(team.color()) + "_WOOL")))
                        .setAmount(16);
                ItemStack boughtWool = builder.build();
                player.getInventory().addItem(boughtWool);
            }
            case TERRACOTTA -> {
                ItemBuilder builder = new ItemBuilder(Objects.requireNonNull(
                        Material.getMaterial(PlayerUtil.materialString(team.color()) + "_TERRACOTTA")))
                        .setAmount(16);
                ItemStack boughtTerracotta = builder.build();
                player.getInventory().addItem(boughtTerracotta);
            }
            case GLASS -> {
                ItemBuilder builder = new ItemBuilder(Objects.requireNonNull(
                        Material.getMaterial(PlayerUtil.materialString(team.color()) + "_STAINED_GLASS")))
                        .setAmount(4);
                ItemStack boughtGlass = builder.build();
                player.getInventory().addItem(boughtGlass);
            }
        }
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

    private String getCurrencyString(String toCheck) {
        Map<String, String> COLOR_TO_CURRENCY = Map.of(
                "white", "iron",
                "gold", "gold",
                "dark_green", "emerald",
                "aqua", "diamond"
        );

        String color = toCheck.split("<")[2].split(">")[0];
        return COLOR_TO_CURRENCY.get(color);
    }

    private int extractAmount(String input) {
        String[] parts = input.split(">");
        String amountPart = parts[2].trim().split(" ")[0];
        return Integer.parseInt(amountPart);
    }

    private void refundCurrency(Player player, Material currency, int amount) {
        player.getInventory().addItem(new ItemBuilder(currency)
                .setAmount(amount)
                .build());
    }

    private void handleEnchantmentPurchase(Player player, int amount, Material material, Map<Enchantment, Integer> enchantments) {
        ItemStack builder = new ItemBuilder(material)
                .setAmount(amount)
                .set(DataComponentTypes.ENCHANTMENTS,
                        ItemEnchantments.itemEnchantments().addAll(enchantments))
                .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable()).build();
        player.getInventory().addItem(builder);
    }

    private void handleEnchantmentPurchase(Player player, int amount, Material material, Enchantment enchantment, int strength) {
        ItemStack builder = new ItemBuilder(material)
                .setAmount(amount)
                .set(DataComponentTypes.ENCHANTMENTS,
                        ItemEnchantments.itemEnchantments().add(enchantment, strength))
                .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable()).build();
        player.getInventory().addItem(builder);
    }

    private void handleSwordPurchase(Player player, Material material) {
        ItemStack bought = new ItemBuilder(material)
                .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable()).build();

        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType() == Material.WOODEN_SWORD) {
                inventory.setItem(i, bought);
                return;
            }
        }
        player.getInventory().addItem(bought);
    }

    private void handleArmorPurchase(Player player, Material material, ItemStack currency, int cost) {
        ItemStack currentBoots = player.getInventory().getBoots();
        if (currentBoots == null) return;

        if (ARMOR_RANKING.containsKey(currentBoots.getType())) {
            int currentRank = ARMOR_RANKING.get(currentBoots.getType());
            int newRank = ARMOR_RANKING.get(material);
            if (newRank < currentRank) {
                player.sendMessage(Component.translatable("player.inventory.armor.too_weak"));
                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.5F, 1.0F); //TODO: Change sound to hypixel sound
                refundCurrency(player, currency.getType(), cost);
                return;
            }
        }

        //Handle player already having the bought armor.
        if (currentBoots.getType() == material) {
            player.sendMessage(Component.translatable("player.inventory.armor.present"));
            player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.5F, 1.0F); //TODO: Change sound to hypixel sound
            //Give player his spent currency back
            refundCurrency(player, currency.getType(), cost);
            return;
        }

        Material leggingsType = switch (material) {
            case CHAINMAIL_BOOTS -> Material.CHAINMAIL_LEGGINGS;
            case IRON_BOOTS -> Material.IRON_LEGGINGS;
            case DIAMOND_BOOTS -> Material.DIAMOND_LEGGINGS;
            default -> null;
        };

        if (leggingsType != null) {
            ItemStack boots = new ItemBuilder(material)
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable()).build();
            ItemStack leggings = new ItemBuilder(leggingsType)
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable()).build();
            player.getInventory().setBoots(boots);
            player.getInventory().setLeggings(leggings);
        }

    }
}
