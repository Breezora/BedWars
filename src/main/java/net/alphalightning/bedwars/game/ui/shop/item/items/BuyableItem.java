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
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
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

    private static final List<Material> PICKAXE_TIERS = List.of(
            Material.WOODEN_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.DIAMOND_PICKAXE
    );

    private static final List<Material> AXE_TIERS = List.of(
            Material.WOODEN_AXE,
            Material.IRON_AXE,
            Material.GOLDEN_AXE,
            Material.DIAMOND_AXE
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
        Material displayMaterial = itemMaterial;
        String nameKey = itemNameKey;
        String priceKey = itemLore.getFirst();

        if(itemMaterial.name().endsWith("_PICKAXE")) {
            Material next = getNextToolTier(viewer, PICKAXE_TIERS);
            if (next != null) {
                displayMaterial = next;
                nameKey = getTranslationKeyForMaterial(next);
                priceKey = getPriceKeyForMaterial(next);
            } else {
                displayMaterial = Material.DIAMOND_PICKAXE;
                nameKey = getTranslationKeyForMaterial(displayMaterial);
                priceKey = getPriceKeyForMaterial(displayMaterial);
            }
        } else if (itemMaterial.name().endsWith("_AXE")) {
            Material next = getNextToolTier(viewer, AXE_TIERS);
            if (next != null) {
                displayMaterial = next;
                nameKey = getTranslationKeyForMaterial(next);
                priceKey = getPriceKeyForMaterial(next);
            } else {
                displayMaterial = Material.DIAMOND_AXE;
                nameKey = getTranslationKeyForMaterial(displayMaterial);
                priceKey = getPriceKeyForMaterial(displayMaterial);
            }
        }

        final ItemBuilder builder = new ItemBuilder(displayMaterial)
                .setName(GlobalTranslator.render(Component.translatable(nameKey), viewer.locale()))
                .setAmount(itemAmount);

        List<Component> lore = new ArrayList<>();
        for (String line : itemLore) {
            if(line.equals(itemLore.getFirst())) {
                lore.add(GlobalTranslator.render(Component.translatable(priceKey), viewer.locale()));
            } else {
            lore.add(GlobalTranslator.render(Component.translatable(line), viewer.locale()));
            }
        }

        String priceTag = getPriceTag(viewer);
        ItemStack currency = getCurrency(priceTag);
        int cost = extractAmount(priceTag);

        lore.add(Component.text(""));

        if (hasEnoughCurrency(viewer, currency, cost)) {
            lore.add(GlobalTranslator.render(Component.translatable("gui.shop.itemshop.buyable.lore.enough"), viewer.locale()));
        } else {
            String notEnoughKey = getCurrencyLoreKey(currency.getType());
            lore.add(GlobalTranslator.render(Component.translatable(notEnoughKey), viewer.locale()));
        }
//        for (String s : itemLore) {
//            if (s.isEmpty()) {
//                lore.add(Component.empty());
//                continue;
//            }
//            lore.add(GlobalTranslator.render(Component.translatable(s), viewer.locale()));
//        }
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
                //Handle buying of Armor. This implies, that the price can be changed, but not the currency of the armor.
                else if (type.name().endsWith("_BOOTS")) {
                    handleArmorPurchase(player, type, currency, cost);
                    return;
                }
                //Handle buying of Tools
                else if (type.name().endsWith("_PICKAXE") || type.name().endsWith("_AXE") || type == Material.SHEARS) {
                    handleToolPurchase(player, type, currency.getType(), cost);
                    this.notifyWindows();
                    return;
                }
                else if (type == Material.FIRE_CHARGE) {
                    NamespacedKey key = new NamespacedKey(plugin, "fireball");
                    ItemStack bought = new ItemBuilder(type)
                            .setName("Fireball")
                            .setAmount(itemAmount).build();
                    ItemMeta meta = bought.getItemMeta();
                    meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
                    bought.setItemMeta(meta);
                    player.getInventory().addItem(bought);
                    return;
                }
                ItemStack bought = new ItemBuilder(type)
                        .setAmount(itemAmount).build();
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
                ItemStack boughtWool = new ItemBuilder(Objects.requireNonNull(
                        Material.getMaterial(PlayerUtil.materialString(team.color()) + "_WOOL")))
                        .setAmount(16).build();
                player.getInventory().addItem(boughtWool);
            }
            case TERRACOTTA -> {
                ItemStack boughtTerracotta = new ItemBuilder(Objects.requireNonNull(
                        Material.getMaterial(PlayerUtil.materialString(team.color()) + "_TERRACOTTA")))
                        .setAmount(16).build();
                player.getInventory().addItem(boughtTerracotta);
            }
            case GLASS -> {
                ItemStack boughtGlass = new ItemBuilder(Objects.requireNonNull(
                        Material.getMaterial(PlayerUtil.materialString(team.color()) + "_STAINED_GLASS")))
                        .setAmount(4).build();
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
        ItemStack bought = new ItemBuilder(material)
                .setAmount(amount)
                .set(DataComponentTypes.ENCHANTMENTS,
                        ItemEnchantments.itemEnchantments().addAll(enchantments))
                .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable()).build();
        player.getInventory().addItem(bought);
    }

    private void handleEnchantmentPurchase(Player player, int amount, Material material, Enchantment enchantment, int strength) {
        ItemStack bought = new ItemBuilder(material)
                .setAmount(amount)
                .set(DataComponentTypes.ENCHANTMENTS,
                        ItemEnchantments.itemEnchantments().add(enchantment, strength))
                .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable()).build();
        player.getInventory().addItem(bought);
    }

    private void handleSwordPurchase(Player player, Material material) {
        ItemStack bought = new ItemBuilder(material)
                .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable()).build();

        int itemSlot = findItemInPlayerInventory(player, Material.WOODEN_SWORD);

        PlayerInventory inventory = player.getInventory();
        if (itemSlot != -1) {
            inventory.setItem(itemSlot, bought);
            return;
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

    private void handleToolPurchase(Player player, Material item, Material currency, int cost) {
        int itemSlot = findItemInPlayerInventory(player, item);
        if (item == Material.SHEARS) {
            if (itemSlot != -1) {
                player.sendMessage(Component.translatable("player.inventory.shears.present"));
                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.5F, 1.0F); //TODO: Change sound to hypixel sound
                refundCurrency(player, currency, cost);
            }
            ItemStack bought = new ItemBuilder(Material.SHEARS)
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable()).build();
            player.getInventory().addItem(bought);
        } else if (item.name().endsWith("_PICKAXE")) {
            Material nextTier = getNextToolTier(player, PICKAXE_TIERS);

            if (nextTier == null) {
                player.sendMessage(Component.translatable("player.inventory.pickaxe.present"));
                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.5F, 1.0F);
                refundCurrency(player, currency, cost);
                return;
            }

            removeLowerTool(player, PICKAXE_TIERS);
            giveUnbreakableItem(player, nextTier);
        } else if (item.name().endsWith("_AXE")) {
            Material nextTier = getNextToolTier(player, AXE_TIERS);

            if (nextTier == null) {
                player.sendMessage(Component.translatable("player.inventory.axe.present"));
                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.5F, 1.0F);
                refundCurrency(player, currency, cost);
                return;
            }

            removeLowerTool(player, AXE_TIERS);
            giveUnbreakableItem(player, nextTier);
        }
    }

    private int findItemInPlayerInventory(Player player, Material toFind) {
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType() == toFind) {
                return i;
            }
        }
        return -1;
    }

    private Material getNextToolTier(Player player, List<Material> tiers) {
        for (int i = 0; i < tiers.size(); i++) {
            Material material = tiers.get(i);
            if (hasItemInInventory(player, material)) {
                if (i + 1 < tiers.size()) {
                    return tiers.get(i + 1);
                } else {
                    return null; // Maxed out
                }
            }
        }
        // No tool found, give him first tier.
        return tiers.getFirst();
    }

    private boolean hasItemInInventory(Player player, Material material) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == material) {
                return true;
            }
        }
        return false;
    }

    private void removeLowerTool(Player player, List<Material> tiers) {
        PlayerInventory inv = player.getInventory();
        for (Material mat : tiers) {
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack item = inv.getItem(i);
                if (item != null && item.getType() == mat) {
                    inv.clear(i);
                    return;
                }
            }
        }
    }

    private void giveUnbreakableItem(Player player, Material material) {
        ItemStack tool = new ItemBuilder(material)
                .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                .build();
        player.getInventory().addItem(tool);
    }

    private String getTranslationKeyForMaterial(Material material) {
        return switch (material) {
            case WOODEN_PICKAXE -> "gui.shop.itemshop.buyable.woodpickaxe.name";
            case IRON_PICKAXE -> "gui.shop.itemshop.buyable.ironpickaxe.name";
            case GOLDEN_PICKAXE -> "gui.shop.itemshop.buyable.goldpickaxe.name";
            case DIAMOND_PICKAXE -> "gui.shop.itemshop.buyable.diamondpickaxe.name";
            case WOODEN_AXE -> "gui.shop.itemshop.buyable.woodaxe.name";
            case GOLDEN_AXE -> "gui.shop.itemshop.buyable.goldaxe.name";
            case IRON_AXE -> "gui.shop.itemshop.buyable.ironaxe.name";
            case DIAMOND_AXE -> "gui.shop.itemshop.buyable.diamondaxe.name";
            default -> "gui.shop.itemshop.buyable.unknown";
        };
    }

    private String getPriceKeyForMaterial(Material material) {
        return switch (material) {
            case WOODEN_PICKAXE -> "gui.shop.itemshop.buyable.woodpickaxe.price";
            case IRON_PICKAXE -> "gui.shop.itemshop.buyable.ironpickaxe.price";
            case GOLDEN_PICKAXE -> "gui.shop.itemshop.buyable.goldpickaxe.price";
            case DIAMOND_PICKAXE -> "gui.shop.itemshop.buyable.diamondpickaxe.price";
            case WOODEN_AXE -> "gui.shop.itemshop.buyable.woodaxe.price";
            case GOLDEN_AXE -> "gui.shop.itemshop.buyable.goldaxe.price";
            case IRON_AXE -> "gui.shop.itemshop.buyable.ironaxe.price";
            case DIAMOND_AXE -> "gui.shop.itemshop.buyable.diamondaxe.price";
            default -> "gui.shop.itemshop.buyable.price.unknown";
        };
    }
    private String getCurrencyLoreKey(Material currency) {
        return switch (currency) {
            case IRON_INGOT -> "gui.shop.itemshop.buyable.lore.not-enough-iron";
            case GOLD_INGOT -> "gui.shop.itemshop.buyable.lore.not-enough-gold";
            case EMERALD -> "gui.shop.itemshop.buyable.lore.not-enough-emerald";
            case DIAMOND -> "gui.shop.itemshop.buyable.lore.not-enough-diamond";
            default -> "gui.shop.itemshop.buyable.lore.not-enough-unknown";
        };
    }
}
