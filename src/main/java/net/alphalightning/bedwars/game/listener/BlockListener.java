package net.alphalightning.bedwars.game.listener;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.state.states.LobbyState;
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

    public BlockListener(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (plugin.gameStateContext().currentState() instanceof LobbyState) {
            event.setCancelled(true);
            return;
        }
        Block block = event.getBlock();
        PersistentDataContainer data = block.getChunk().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "custom");

        data.set(key, PersistentDataType.BOOLEAN, true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        PersistentDataContainer data = block.getChunk().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "custom");

        if (data.has(key, PersistentDataType.BOOLEAN)) {
            data.remove(key);
        } else {
            event.setCancelled(true);
        }

    }
}
