package net.alphalightning.bedwars.setup.ui.simulation;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Diese Klasse dient zum Simulieren von Stages außerhalb des Setups
 */
public class StageSimulation implements Listener {

    private final NamespacedKey key = new NamespacedKey("bedwars", "stage");

    private final int stage;

    public StageSimulation(BedWarsPlugin plugin, Player player, int stage) {
        this.stage = stage;
        simulate(player);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        simulate(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer container = player.getPersistentDataContainer();

        if (container.has(key)) {
            container.remove(key); // Remove the key
        }
    }

    private void simulate(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();

        if (container.has(key)) {
            container.remove(key); // Remove the key
        }
        container.set(key, PersistentDataType.INTEGER, stage); // Simulate stage
    }

}
