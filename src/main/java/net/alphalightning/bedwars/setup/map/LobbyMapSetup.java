package net.alphalightning.bedwars.setup.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.jackson.LobbyLocations;
import net.alphalightning.bedwars.setup.map.jackson.SimpleJacksonLocation;
import net.alphalightning.bedwars.setup.map.stages.CancelStage;
import net.alphalightning.bedwars.setup.map.stages.lobby.CompleteSetupStage;
import net.alphalightning.bedwars.setup.map.stages.lobby.ConfigureHologramStage;
import net.alphalightning.bedwars.setup.map.stages.lobby.ConfigureSpawnStage;
import net.alphalightning.bedwars.setup.map.stages.lobby.WelcomeStage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Map;

public final class LobbyMapSetup implements MapSetup {

    private static final String FILE_NAME = "lobby.json";

    private final CancelStage cancelStage;

    private final BedWarsPlugin plugin;

    private Player player;
    private int stage;

    private Location spawn;
    private Location hologram;

    public LobbyMapSetup(BedWarsPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.cancelStage = new CancelStage(plugin, player, this, true);

        startStage(0);
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
            case 0 -> new WelcomeStage(plugin, player, this).run();
            case 1 -> new ConfigureSpawnStage(plugin, player, this).run();
            case 2 -> new ConfigureHologramStage(plugin, player, this).run();
            case 3 -> new CompleteSetupStage(plugin, player, this, FILE_NAME).run();
            default -> cancelStage.run();
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

    @Override
    public int stage() {
        return stage;
    }

    public void spawn(Location spawn) {
        this.spawn = spawn;
    }

    public void hologram(Location hologram) {
        this.hologram = hologram;
    }
}
