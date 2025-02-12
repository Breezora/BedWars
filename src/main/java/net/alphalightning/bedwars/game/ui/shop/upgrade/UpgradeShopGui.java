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

    private final Player player;
    private final Single gui;

    public UpgradeShopGui(Player player) {
        this.player = player;
        this.gui = createGui();
    }

    private Single createGui() {
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
                                "gui.shop.upgrade.category.sharpness.lore", "gui.shop.upgrade.category.sharpness.lore.price", "gui.shop.upgrade.error.diamonds"))
                        .addIngredient('b', new UpgradeItem("gui.shop.upgrade.category.protection", Material.IRON_CHESTPLATE, 1,
                                "gui.shop.upgrade.category.protection.lore", "gui.shop.upgrade.category.protection.lore.price", "gui.shop.upgrade.error.diamonds"))
                        .addIngredient('c', new UpgradeItem("gui.shop.upgrade.category.haste", Material.GOLDEN_PICKAXE, 1,
                                "gui.shop.upgrade.category.haste.lore", "gui.shop.upgrade.category.haste.lore.price", "gui.shop.upgrade.error.diamonds"))
                        .addIngredient('d', new UpgradeItem("gui.shop.upgrade.category.blindness", Material.TRIPWIRE_HOOK, 1,
                                "gui.shop.upgrade.category.blindness.lore", "gui.shop.upgrade.category.blindness.lore.price", "gui.shop.upgrade.error.diamonds"))
                        .addIngredient('e', new UpgradeItem("gui.shop.upgrade.category.counterstrike", Material.FEATHER, 1,
                                "gui.shop.upgrade.category.counterstrike.lore", "gui.shop.upgrade.category.counterstrike.lore.price", "gui.shop.upgrade.error.diamonds"))
                        .addIngredient('f', new UpgradeItem("gui.shop.upgrade.category.reveal", Material.REDSTONE_TORCH, 1,
                                "gui.shop.upgrade.category.reveal.lore", "gui.shop.upgrade.category.reveal.lore.price", "gui.shop.upgrade.error.diamonds"))
                        .addIngredient('g', new UpgradeItem("gui.shop.upgrade.category.resources", Material.FURNACE, 1,
                                "gui.shop.upgrade.category.resources.lore", "gui.shop.upgrade.category.resources.lore.price", "gui.shop.upgrade.error.diamonds"))
                        .addIngredient('h', new UpgradeItem("gui.shop.upgrade.category.healing", Material.BEACON, 1,
                                "gui.shop.upgrade.category.healing.lore", "gui.shop.upgrade.category.healing.lore.price", "gui.shop.upgrade.error.diamonds"))
                        .addIngredient('i', new UpgradeItem("gui.shop.upgrade.category.featherfalling", Material.DIAMOND_BOOTS, 1,
                                "gui.shop.upgrade.category.featherfalling.lore", "gui.shop.upgrade.category.featherfalling.lore.price", "gui.shop.upgrade.error.diamonds"))
                        .addIngredient('j', new UpgradeItem("gui.shop.upgrade.category.mining-fatigue", Material.IRON_PICKAXE, 1,
                                "gui.shop.upgrade.category.mining-fatigue.lore", "gui.shop.upgrade.category.mining-fatigue.lore.price", "gui.shop.upgrade.error.diamonds"))
                        .addIngredient('k', new QueueItem("gui.shop.upgrade.traps.queue.first", 1,
                                "gui.shop.upgrade.traps.queue.first.lore", "gui.shop.upgrade.traps.queue.description", "gui.shop.upgrade.traps.queue.price.next"))
                        .addIngredient('l', new QueueItem("gui.shop.upgrade.traps.queue.second", 2,
                                "gui.shop.upgrade.traps.queue.second.lore", "gui.shop.upgrade.traps.queue.description", "gui.shop.upgrade.traps.queue.price.next"))
                        .addIngredient('m', new QueueItem("gui.shop.upgrade.traps.queue.third", 3,
                                "gui.shop.upgrade.traps.queue.third.lore", "gui.shop.upgrade.traps.queue.description", "gui.shop.upgrade.traps.queue.price.next"))
                )
                .setTitle(Component.translatable("gui.shop.upgrade.name"));
    }

    public void showGui() {
        this.gui.open(this.player);
    }

}
