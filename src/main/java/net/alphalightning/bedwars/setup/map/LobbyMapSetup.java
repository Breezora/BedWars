package net.alphalightning.bedwars.setup.map;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.jackson.Lobby;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.io.File;
import java.io.IOException;

public final class LobbyMapSetup implements MapSetup, Listener {

    private static final String FILE_NAME = "lobby.json";

    private final BedWarsPlugin plugin;
    private final ComponentLogger logger;
    private final File file;

    private Player player;
    private int stage;

    private Location spawn;
    private Location hologram;

    public LobbyMapSetup(BedWarsPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.logger = plugin.getComponentLogger();
        this.file = new File(plugin.getDataFolder().getPath() + "/config/" + FILE_NAME);

        startStage(0);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void start() {
        startStage(1);
    }

    @Override
    public void finish() {
        startStage(3);
        saveConfiguration();
    }

    @Override
    public void cancel() {
        startStage(-1);
        player = null;
    }

    @Override
    public void saveConfiguration() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            plugin.jsonMapper().writeValue(file, new Lobby(spawn, hologram));

        } catch (IOException e) {
            plugin.getLogger().severe("Could not save file " + FILE_NAME + ": " + e.getMessage());
        }
    }

    private void startStage(int stage) {
        if (this.stage > stage && stage == -1) {
            throw new IllegalStateException("Could not start stage " + stage + ": The new stage must be at least the current stage (" + this.stage + ") or higher.");
        }
        this.stage = stage;

        switch (stage) {
            case 0 -> player.sendMessage(Component.translatable("lobbysetup.start"));
            case 1 -> player.sendMessage(Component.translatable("lobbysetup.stage1"));
            case 2 -> player.sendMessage(Component.translatable("lobbysetup.stage2"));
            case 3 -> player.sendMessage(Component.translatable("lobbysetup.finish", Component.text(FILE_NAME)));
            default -> {
                player.sendMessage(Component.translatable("lobbysetup.cancel"));
                logger.warn(Component.translatable("lobbysetup.cancel"));
            }
        }
    }

    // Start stage logic

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if (this.player.equals(player) || !event.isSneaking()) {
            return;
        }

        Location location = player.getLocation();
        if (stage == 1) {
            spawn = location;
            startStage(2);

        } else if (stage == 2) {
            hologram = location;
            finish();
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (!this.player.equals(event.getPlayer()) || !((stage == 1) || (stage == 2))) {
            return;
        }

        if (event.signedMessage().message().equalsIgnoreCase("cancel")) {
            event.setCancelled(true);
            cancel();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.player.equals(event.getPlayer())) {
            cancel();
        }
    }
}
