package net.alphalightning.bedwars.util;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.item.ItemBuilder;

public final class SelectionWandTool implements Listener {

    private final Player owner;
    private final ItemStack tool;
    private Location first, second;

    public SelectionWandTool(BedWarsPlugin plugin, Player owner) {
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

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public Location first() {
        return first;
    }

    public Location second() {
        return second;
    }

    //TODO: Das hier nicht als Listener haben, sondern als Methode useWand() haben und im Setup ausführen
    @EventHandler
    public void onToolUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (player != this.owner) return;
        if (block == null) return;
        if (!player.getInventory().getItemInMainHand().equals(tool)) return;

        event.setCancelled(true);

        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK -> first(block.getLocation());
            case RIGHT_CLICK_BLOCK -> second(block.getLocation());
        }
    }

    private void first(Location first) {
        if (this.first == null || second == null) {
            updateFirst(first);
            return;
        }
        if (first.equals(second)) {
            owner.sendMessage(Component.translatable("mapsetup.stage.16.same"));
        }
    }

    private void second(Location second) {
        if (this.first == null || second == null) {
            updateSecond(second);
            return;
        }
        if (first.equals(second)) {
            owner.sendMessage(Component.translatable("mapsetup.stage.16.same"));
        }
    }

    private void updateFirst(Location first) {
        this.first = first;
        owner.sendMessage(Component.translatable("mapsetup.stage.16.first"));
    }

    private void updateSecond(Location second) {
        this.second = second;
        owner.sendMessage(Component.translatable("mapsetup.stage.16.second"));
    }
}
