package net.alphalightning.bedwars.setup.map;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.jackson.LobbyLocations;
import net.alphalightning.bedwars.setup.map.jackson.SimpleJacksonLocation;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.io.IOException;
import java.util.Map;

public final class LobbyMapSetup implements MapSetup, Listener {

    private static final String FILE_NAME = "lobby.json";

    private final BedWarsPlugin plugin;

    private Player player;
    private int stage;

    private Location spawn;
    private Location hologram;

    public LobbyMapSetup(BedWarsPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        startStage(0);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void invalidate() {
        player = null;
        spawn = null;
        hologram = null;
    }

    @Override
    public void startStage(int stage) {
        this.stage = validateStage(this.stage, stage);

        switch (stage) {
            case 0 -> {
                Feedback.start(player);
                player.sendMessage(Component.translatable("lobbysetup.start"));
            }
            case 1 -> player.sendMessage(Component.translatable("lobbysetup.stage1")); // Kein Sound, da Interferenz mit Stage 0
            case 2 -> {
                Feedback.success(player);
                player.sendMessage(Component.translatable("lobbysetup.stage2"));
            }
            case 3 -> {
                Feedback.complete(player);
                player.sendMessage(Component.translatable("lobbysetup.finish", Component.text(FILE_NAME)));
            }
            default -> {
                Feedback.error(player);

                Component component = Component.translatable("lobbysetup.cancel");
                player.sendMessage(component);
                plugin.getComponentLogger().warn(component);
            }
        }
    }

    @Override
    public void saveConfiguration() {
        try {
            createDirectory();

            SimpleJacksonLocation spawnLocation = new SimpleJacksonLocation(spawn);
            SimpleJacksonLocation hologramLocation = new SimpleJacksonLocation(hologram);

            Map<String, SimpleJacksonLocation> locationsMap = Map.of("spawn", spawnLocation, "hologram", hologramLocation);
            plugin.jsonMapper().writeValue(directory().resolve(FILE_NAME).toFile(), new LobbyLocations(locationsMap));

        } catch (IOException exception) {
            plugin.getLogger().severe("Could not save file " + FILE_NAME + ": " + exception.getMessage());
        }
    }

    @Override
    public BedWarsPlugin plugin() {
        return plugin;
    }

    // Start stage logic

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if (this.player == null || !this.player.equals(player)) {
            return;
        }

        Location location = player.getLocation();

        if (!player.isSneaking() || location.subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
            return;
        }

        if (stage == 1) {
            spawn = location;
            startStage(2);

        } else if (stage == 2) {
            hologram = location;
            finish(3);
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (!player.equals(event.getPlayer()) || !((stage == 1) || (stage == 2))) {
            return;
        }

        if (event.signedMessage().message().equalsIgnoreCase("cancel")) {
            event.setCancelled(true);
            cancel();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (player != null && player.equals(event.getPlayer())) {
            cancel();
        }
    }
}
