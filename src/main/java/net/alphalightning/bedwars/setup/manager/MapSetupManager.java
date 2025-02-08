package net.alphalightning.bedwars.setup.manager;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.provider.ServiceProvider;
import net.alphalightning.bedwars.setup.ConfigurationType;
import net.alphalightning.bedwars.setup.Setup;
import net.alphalightning.bedwars.setup.map.LobbyConfiguration;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapSetupManager implements ServiceProvider<MapSetup>, LobbyConfiguration {

    private static MapSetupManager instance;

    private final VisualizationManager visualizationManager = VisualizationManager.instance();

    private final Map<Player, MapSetup> activeSetups;
    private final Map<String, MapSetup> activeMaps;

    private BedWarsPlugin plugin;
    private ConfigurationType configurationType;
    private Player executor;
    private String mapName;

    /*
    IMPORTANT:
    We are using a singleton with the monitor keyword "synchronized" to make sure
    that only one player at a time can access this instance. This allows us to
    prevent duplications in map names or in active player setups.
     */

    // Start singleton instance

    private MapSetupManager() {
        this.activeSetups = new ConcurrentHashMap<>();
        this.activeMaps = new ConcurrentHashMap<>();
    }

    public static synchronized MapSetupManager instance() {
        if (instance == null) {
            instance = new MapSetupManager();
        }
        return instance;
    }

    // End singleton instance

    public synchronized MapSetupManager prepareNewSetup(BedWarsPlugin plugin, ConfigurationType configurationType, Player executor, String mapName) {
        this.plugin = plugin;
        this.configurationType = configurationType;
        this.executor = executor;
        this.mapName = mapName;
        return this;
    }

    @Override
    public @NotNull MapSetup get() { // Create fresh setup instance
        MapSetup.Builder builder = Setup.mapBuilder(this.configurationType)
                .plugin(this.plugin)
                .executor(this.executor);

        if (this.configurationType == ConfigurationType.MAP) {
            builder.name(this.mapName);
        }
        return builder.build();
    }

    public synchronized void startSetup() {
        if (hasActiveSetup(this.executor)) { // Player is already running a setup
            this.executor.sendMessage(Component.translatable("error.setup.running.self"));
            Feedback.error(this.executor);
            return;
        }

        final MapSetup setup = get();
        final String name = setup.mapName();

        if (activeMaps.containsKey(toLowerCase(name))) { // A map with that is currently configured by someone else
            if (name.equalsIgnoreCase(LOBBY_MAP_NAME)) {
                this.executor.sendMessage(Component.translatable("error.setup.running.lobby"));
            } else {
                this.executor.sendMessage(Component.translatable("error.setup.running.game", Component.text(name)));
            }
            Feedback.error(this.executor);
            setup.cancel(true);
            return;
        }

        // Start map configuration

        activeMaps.put(toLowerCase(name), setup);
        activeSetups.put(this.executor, setup);
        setup.start();
    }

    public synchronized void finishSetup(Player player, int lastStage) {
        final MapSetup setup = activeSetups.remove(player);
        if (setup == null) {
            return;
        }

        setup.finish(lastStage);
        this.activeMaps.remove(toLowerCase(setup.mapName()));

        Bukkit.getScheduler().runTaskLater(this.plugin, () -> this.visualizationManager.unregisterAll(setup), 30 * 20L);
    }

    public synchronized void cancelSetup(Player player) {
        final MapSetup setup = activeSetups.remove(player);
        if (setup == null) {
            return;
        }

        setup.cancel(false);
        this.activeMaps.remove(toLowerCase(setup.mapName()));
        this.visualizationManager.unregisterAll(setup);
    }

    public synchronized boolean hasActiveSetup(Player player) {
        return this.activeSetups.containsKey(player);
    }

    private @NotNull String toLowerCase(@NotNull String string) {
        return string.toLowerCase();
    }
}
