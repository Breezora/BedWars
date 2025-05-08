package net.alphalightning.bedwars.game.state.states;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DyedItemColor;
import net.alphalightning.bedwars.game.map.MapManager;
import net.alphalightning.bedwars.game.state.AbstractGameState;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.alphalightning.bedwars.game.team.Team;
import net.alphalightning.bedwars.game.team.allocator.DynamicTeamAllocator;
import net.alphalightning.bedwars.game.team.allocator.TeamAllocator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.item.ItemBuilder;

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

        for (Team team : teams) {
            int color = team.color();

            ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .build();
            ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .build();
            ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .build();
            ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .build();

            for (Player player : team.players()) {
                player.getInventory().setArmorContents(new ItemStack[]{
                        boots, leggings, chestplate, helmet
                });
            }
        }
    }

}
