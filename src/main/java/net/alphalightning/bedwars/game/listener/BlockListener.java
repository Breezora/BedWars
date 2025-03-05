package net.alphalightning.bedwars.game.listener;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BlockListener implements Listener {

    protected final BedWarsPlugin plugin;
    public static boolean enabled = false;

    public BlockListener(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(enabled) {
            Block block = event.getBlock();
            PersistentDataContainer data = block.getChunk().getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin, "custom");

            data.set(key, PersistentDataType.BOOLEAN, true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (enabled) {
            Block block = event.getBlock();
            PersistentDataContainer data = block.getChunk().getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin, "custom");

            if (data.has(key, PersistentDataType.BOOLEAN)) {
                event.getPlayer().sendMessage("Dieser Block wurde von einem Spieler platziert!");
                data.remove(key); // Entfernt den Eintrag, falls der Block abgebaut wird
            } else {
                event.getPlayer().sendMessage("Map Block!");
                event.setCancelled(true);
            }
        }
    }
}
