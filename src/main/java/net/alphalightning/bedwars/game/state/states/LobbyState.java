package net.alphalightning.bedwars.game.state.states;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.state.AbstractGameState;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.alphalightning.bedwars.setup.map.jackson.LobbyLocations;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

import static net.alphalightning.bedwars.setup.map.LobbyConfiguration.LOBBY_FILE_NAME;

public class LobbyState extends AbstractGameState implements Listener {

    private final BedWarsPlugin plugin;

    public LobbyState(@NotNull BedWarsPlugin plugin, GameStateContext context) {
        super(context);
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void start() {
        context.logger().info(Component.translatable("state.lobby.start"));
    }

    @Override
    public void stop() {
        context.logger().info(Component.translatable("state.lobby.stop"));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.gameStateContext().currentState() instanceof LobbyState) {
            Player player = event.getPlayer();
            try {
                LobbyLocations lobbyLocations = plugin.jsonMapper().readValue(Path.of("maps/lobby.json").toFile(), LobbyLocations.class);
                Location spawn = lobbyLocations.get("spawn").asBukkitLocation();
                if (spawn == null) return;
                player.teleport(spawn);
            } catch (IOException exception) {
                plugin.getLogger().severe("Could not read file " + LOBBY_FILE_NAME + ": " + exception.getMessage());
            }
        }
    }

}
