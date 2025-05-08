package net.alphalightning.bedwars.game.state.states;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class InGameState extends AbstractGameState {

    private final MapManager mapManager;
    private List<Team> teams;

    public InGameState(GameStateContext context, MapManager mapManager) {
        super(context);
        this.mapManager = mapManager;
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
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta helmetItemMeta = (LeatherArmorMeta) helmet.getItemMeta();
        LeatherArmorMeta chestplateItemMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        LeatherArmorMeta leggingsItemMeta = (LeatherArmorMeta) leggings.getItemMeta();
        LeatherArmorMeta bootsItemMeta = (LeatherArmorMeta) boots.getItemMeta();

        for (Team team : teams) {
            int color = team.color();

            helmetItemMeta.setColor(Color.fromRGB(color));
            chestplateItemMeta.setColor(Color.fromRGB(color));
            leggingsItemMeta.setColor(Color.fromRGB(color));
            bootsItemMeta.setColor(Color.fromRGB(color));

            for (Player player : team.players()) {
                player.getInventory().setArmorContents(new ItemStack[]{
                        helmet, chestplate, leggings, boots
                });
            }
        }
    }

}
