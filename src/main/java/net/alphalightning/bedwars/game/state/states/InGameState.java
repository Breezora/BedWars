package net.alphalightning.bedwars.game.state.states;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DyedItemColor;
import io.papermc.paper.datacomponent.item.Unbreakable;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.map.MapManager;
import net.alphalightning.bedwars.game.state.AbstractGameState;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.alphalightning.bedwars.game.team.Team;
import net.alphalightning.bedwars.game.team.allocator.DynamicTeamAllocator;
import net.alphalightning.bedwars.game.team.allocator.TeamAllocator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class InGameState extends AbstractGameState implements Listener {

    private final BedWarsPlugin plugin;
    private final MapManager mapManager;
    private List<Team> teams;

    public InGameState(@NotNull BedWarsPlugin plugin, GameStateContext context, MapManager mapManager) {
        super(context);
        this.plugin = plugin;
        this.mapManager = mapManager;
        Bukkit.getPluginManager().registerEvents(this, plugin);

    }


    @Override
    public void start() {
        TranslatableComponent component = Component.translatable("state.ingame.start");

        Bukkit.broadcast(component);
        context.logger().info(component);

        allocateTeams();
        teleportPlayers();
        preparePlayers();
    }

    @Override
    public void stop() {
        context.logger().info(Component.translatable("state.ingame.stop"));
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (isArmor(item)) {
            event.setCancelled(true);
            return;
        }
        if (item.getType() == Material.WOODEN_SWORD) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> event.getPlayer().spigot().respawn(), 1L);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        for (Team team : teams) {
            Location spawn = team.spawnpoint();

            if (team.players().contains(player)) {
               Bukkit.getScheduler().runTaskLater(plugin, () -> player.teleport(spawn), 1L);
            }
        }
    }

    private void allocateTeams() {
        TeamAllocator teamAllocator = new DynamicTeamAllocator(mapManager.selected().teams());
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        int maxTeams = mapManager.selected().teams().size();
        int teamSize = mapManager.selected().teamSize();

        teams = teamAllocator.allocateTeams(players, maxTeams, teamSize);
    }

    private void teleportPlayers() {
        for (Team team : teams) {
            Location spawn = team.spawnpoint();

            for (Player player : team.players()) {
                player.teleport(spawn);
            }
        }
    }

    private void preparePlayers() {
        for (Team team : teams) {
            int color = team.color();

            ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                    .build();
            ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                    .build();
            ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                    .build();
            ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .build();

            ItemStack woodSword = new ItemBuilder(Material.WOODEN_SWORD)
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                    .build();

            for (Player player : team.players()) {
                player.getInventory().setArmorContents(new ItemStack[]{boots, leggings, chestplate, helmet});
                player.getInventory().setItem(0, woodSword);
            }
        }
    }

    private boolean isArmor(ItemStack item) {
        return switch (item.getType()) {
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS, DIAMOND_BOOTS, DIAMOND_LEGGINGS, CHAINMAIL_BOOTS, CHAINMAIL_LEGGINGS, IRON_BOOTS, IRON_LEGGINGS -> true;
            default -> false;
        };
    }

}
