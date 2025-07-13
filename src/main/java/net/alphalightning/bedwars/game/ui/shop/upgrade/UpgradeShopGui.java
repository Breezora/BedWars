package net.alphalightning.bedwars.game.ui.shop.upgrade;

import net.alphalightning.bedwars.game.ui.shop.upgrade.items.QueueItem;
import net.alphalightning.bedwars.game.ui.shop.upgrade.items.UpgradeItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

public class UpgradeShopGui {

    private final Single gui;

    public UpgradeShopGui() {
        this.gui = createGui();
    }

    private Single createGui() {
        final String notEnoughDiamond = "gui.shop.itemshop.buyable.lore.not-enough-diamond";
        return Window.single()
                .setGui(Gui.normal()
                        .setStructure(
                                ". . . . . . . . .",
                                ". a b c . d e f .",
                                ". g h i . j . . .",
                                "# # # # # # # # #",
                                ". . . k l m . . .",
                                ". . . . . . . . ."
                        )
                        .addIngredient('a', new UpgradeItem("gui.shop.upgrade.category.sharpness", Material.IRON_SWORD, 1,
                                "gui.shop.upgrade.category.sharpness.lore",
                                "gui.shop.upgrade.category.sharpness.lore.2",
                                "",
                                "gui.shop.upgrade.category.sharpness.price",
                                "",
                                notEnoughDiamond))
                        .addIngredient('b', new UpgradeItem("gui.shop.upgrade.category.protection", Material.IRON_CHESTPLATE, 1,
                                "gui.shop.upgrade.category.protection.lore",
                                "gui.shop.upgrade.category.protection.lore.2",
                                "",
                                "gui.shop.upgrade.category.protection.lore.price",
                                "gui.shop.upgrade.category.protection.lore.price.2",
                                "gui.shop.upgrade.category.protection.lore.price.3",
                                "gui.shop.upgrade.category.protection.lore.price.4",
                                "",
                                notEnoughDiamond))
                        .addIngredient('c', new UpgradeItem("gui.shop.upgrade.category.haste", Material.GOLDEN_PICKAXE, 1,
                                "gui.shop.upgrade.category.haste.lore",
                                "gui.shop.upgrade.category.haste.lore.2",
                                "",
                                "gui.shop.upgrade.category.haste.lore.price",
                                "gui.shop.upgrade.category.haste.lore.price.2",
                                "",
                                notEnoughDiamond))
                        .addIngredient('d', new UpgradeItem("gui.shop.upgrade.category.blindness", Material.TRIPWIRE_HOOK, 1,
                                "gui.shop.upgrade.category.blindness.lore",
                                "",
                                "gui.shop.upgrade.category.blindness.lore.price",
                                "",
                                notEnoughDiamond))
                        .addIngredient('e', new UpgradeItem("gui.shop.upgrade.category.counterstrike", Material.FEATHER, 1,
                                "gui.shop.upgrade.category.counterstrike.lore",
                                "gui.shop.upgrade.category.counterstrike.lore.2",
                                "gui.shop.upgrade.category.counterstrike.lore.3",
                                "gui.shop.upgrade.category.counterstrike.lore.4",
                                "",
                                "gui.shop.upgrade.category.counterstrike.lore.price",
                                "",
                                notEnoughDiamond))
                        .addIngredient('f', new UpgradeItem("gui.shop.upgrade.category.reveal", Material.REDSTONE_TORCH, 1,
                                "gui.shop.upgrade.category.reveal.lore",
                                "gui.shop.upgrade.category.reveal.lore.2",
                                "",
                                "gui.shop.upgrade.category.reveal.lore.price",
                                "",
                                notEnoughDiamond))
                        .addIngredient('g', new UpgradeItem("gui.shop.upgrade.category.resources", Material.FURNACE, 1,
                                "gui.shop.upgrade.category.resources.lore",
                                "gui.shop.upgrade.category.resources.lore.2",
                                "gui.shop.upgrade.category.resources.lore.3",
                                "",
                                "gui.shop.upgrade.category.resources.lore.price",
                                "gui.shop.upgrade.category.resources.lore.price.2",
                                "gui.shop.upgrade.category.resources.lore.price.3",
                                "gui.shop.upgrade.category.resources.lore.price.4",
                                "",
                                notEnoughDiamond))
                        .addIngredient('h', new UpgradeItem("gui.shop.upgrade.category.healing", Material.BEACON, 1,
                                "gui.shop.upgrade.category.healing.lore",
                                "gui.shop.upgrade.category.healing.lore.2",
                                "",
                                "gui.shop.upgrade.category.healing.lore.price",
                                "",
                                notEnoughDiamond))
                        .addIngredient('i', new UpgradeItem("gui.shop.upgrade.category.featherfalling", Material.DIAMOND_BOOTS, 1,
                                "gui.shop.upgrade.category.featherfalling.lore",
                                "gui.shop.upgrade.category.featherfalling.lore.2",
                                "",
                                "gui.shop.upgrade.category.featherfalling.lore.price",
                                "gui.shop.upgrade.category.featherfalling.lore.price.2",
                                "",
                                notEnoughDiamond))
                        .addIngredient('j', new UpgradeItem("gui.shop.upgrade.category.mining-fatigue", Material.IRON_PICKAXE, 1,
                                "gui.shop.upgrade.category.mining-fatigue.lore",
                                "gui.shop.upgrade.category.mining-fatigue.lore.2",
                                "",
                                "gui.shop.upgrade.category.mining-fatigue.lore.price",
                                "",
                                notEnoughDiamond))
                        .addIngredient('k', new QueueItem("gui.shop.upgrade.traps.queue.first", 1,
                                "gui.shop.upgrade.traps.queue.first.lore",
                                "gui.shop.upgrade.traps.queue.generic.lore.2",
                                "",
                                "gui.shop.upgrade.traps.queue.description",
                                "gui.shop.upgrade.traps.queue.description.2",
                                "gui.shop.upgrade.traps.queue.description.3",
                                "gui.shop.upgrade.traps.queue.description.4",
                                "",
                                "gui.shop.upgrade.traps.queue.price.next"))
                        .addIngredient('l', new QueueItem("gui.shop.upgrade.traps.queue.second", 2,
                                "gui.shop.upgrade.traps.queue.second.lore",
                                "gui.shop.upgrade.traps.queue.generic.lore.2",
                                "",
                                "gui.shop.upgrade.traps.queue.description",
                                "gui.shop.upgrade.traps.queue.description.2",
                                "gui.shop.upgrade.traps.queue.description.3",
                                "gui.shop.upgrade.traps.queue.description.4",
                                "",
                                "gui.shop.upgrade.traps.queue.price.next"))
                        .addIngredient('m', new QueueItem("gui.shop.upgrade.traps.queue.third", 3,
                                "gui.shop.upgrade.traps.queue.third.lore",
                                "gui.shop.upgrade.traps.queue.generic.lore.2",
                                "",
                                "gui.shop.upgrade.traps.queue.description",
                                "gui.shop.upgrade.traps.queue.description.2",
                                "gui.shop.upgrade.traps.queue.description.3",
                                "gui.shop.upgrade.traps.queue.description.4",
                                "",
                                "gui.shop.upgrade.traps.queue.price.next"))
                )
                .setTitle(Component.translatable("gui.shop.upgrade.name"));
    }

    public void showGui(Player player) {
        this.gui.open(player);
    }

}
