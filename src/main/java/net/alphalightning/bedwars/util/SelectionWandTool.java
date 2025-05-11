package net.alphalightning.bedwars.util;

import net.alphalightning.bedwars.feedback.Feedback;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.item.ItemBuilder;

public final class SelectionWandTool {

    private final Player owner;
    private final ItemStack tool;
    private Location first, second;

    public SelectionWandTool(Player owner) {
        this.owner = owner;
        this.tool = new ItemBuilder(Material.ARROW)
                .setCustomName(GlobalTranslator.render(Component.translatable("item.selection_wand"), owner.locale()))
                .addLoreLines(
                        GlobalTranslator.render(Component.translatable("item.selection_wand.left"), owner.locale()),
                        GlobalTranslator.render(Component.translatable("item.selection_wand.right"), owner.locale()))
                .hideTooltip(false)
                .build();

        owner.getInventory().clear();
        owner.getInventory().setItem(0, tool);
    }

    public Location first() {
        return first;
    }

    public Location second() {
        return second;
    }

    public void reset() {
        first = null;
        second = null;
    }

    public void onToolUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (player != this.owner) return;
        if (block == null) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!player.getInventory().getItemInMainHand().equals(tool)) return;

        event.setCancelled(true);

        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK -> first(block.getLocation());
            case RIGHT_CLICK_BLOCK -> second(block.getLocation());
        }
    }

    private void first(Location clicked) {
        if (second != null && second.equals(clicked)) {
            owner.sendMessage(Component.translatable("mapsetup.stage.16.same"));
            Feedback.error(owner);
            return;
        }
        owner.sendMessage(Component.translatable("mapsetup.stage.16.first"));
        first = clicked;
    }

    private void second(Location clicked) {
        if (first != null && first.equals(clicked)) {
            owner.sendMessage(Component.translatable("mapsetup.stage.16.same"));
            Feedback.error(owner);
            return;
        }
        owner.sendMessage(Component.translatable("mapsetup.stage.16.second"));
        second = clicked;
    }

}
