package net.alphalightning.bedwars.game.item;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.alphalightning.bedwars.game.state.states.InGameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class FireballItem implements Listener {

    private final BedWarsPlugin plugin;
    private final GameStateContext context;

    public FireballItem(BedWarsPlugin plugin) {
        this.plugin = plugin;
        this.context = plugin.gameStateContext();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (!(context.currentState() instanceof InGameState)) return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (event.getHand() != EquipmentSlot.HAND) return;
        event.setCancelled(true);

        //Fireball logic
        if (item.getType() != Material.FIRE_CHARGE) return;
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Location eye = player.getEyeLocation();
            Vector direction = eye.getDirection().normalize().multiply(1.5);

            player.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
            player.getWorld().spawn(eye.add(direction.multiply(1.345)), Fireball.class, fireball -> {
                fireball.setDirection(direction);
                fireball.setShooter(player);
                fireball.setIsIncendiary(false);
                fireball.setYield(2.0f);
            });
            //Fireball fireball = player.launchProjectile(Fireball.class);

            //fireball.setIsIncendiary(false);

        }
    }

    public void registerEvent() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
