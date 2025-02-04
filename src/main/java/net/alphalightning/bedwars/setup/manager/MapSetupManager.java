package net.alphalightning.bedwars.setup.manager;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.provider.ServiceProvider;
import net.alphalightning.bedwars.setup.ConfigurationType;
import net.alphalightning.bedwars.setup.Setup;
import net.alphalightning.bedwars.setup.map.LobbyConfiguration;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapSetupManager implements ServiceProvider<MapSetup>, LobbyConfiguration {

    private static MapSetupManager instance;

    private final Map<Player, MapSetup> activeSetups;
    private final Map<String, MapSetup> activeMaps;

    private BedWarsPlugin plugin;
    private ConfigurationType configurationType;
    private Player executor;
    private String mapName;

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

    public synchronized MapSetupManager prepareNewSetup(BedWarsPlugin plugin, ConfigurationType configurationType, Player executor, String mapName) {
        this.plugin = plugin;
        this.configurationType = configurationType;
        this.executor = executor;
        this.mapName = mapName;
        return this;
    }

    @Override
    public @NotNull MapSetup get() {
        MapSetup.Builder builder = Setup.mapBuilder(this.configurationType)
                .plugin(this.plugin)
                .executor(this.executor);

        if (this.configurationType == ConfigurationType.MAP) {
            builder.name(this.mapName);
        }
        return builder.build();
    }

    public synchronized void startSetup() {
        if (hasActiveSetup(this.executor)) {
            this.executor.sendMessage(Component.translatable("error.setup.running.self"));
            Feedback.error(this.executor);
            return;
        }

        final MapSetup setup = get();
        final String name = setup.mapName();

        if (activeMaps.containsKey(toLowerCase(name))) {
            if (name.equalsIgnoreCase(LOBBY_MAP_NAME)) {
                this.executor.sendMessage(Component.translatable("error.setup.running.lobby"));
            } else {
                this.executor.sendMessage(Component.translatable("error.setup.running.game", Component.text(name)));
            }
            Feedback.error(this.executor);
            setup.cancel(true);
            return;
        }

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
        activeMaps.remove(toLowerCase(setup.mapName()));
    }

    public synchronized void cancelSetup(Player player) {
        final MapSetup setup = activeSetups.remove(player);
        if (setup == null) {
            return;
        }

        setup.cancel(false);
        activeMaps.remove(toLowerCase(setup.mapName()));
    }

    public synchronized boolean hasActiveSetup(Player player) {
        return this.activeSetups.containsKey(player);
    }

    private @NotNull String toLowerCase(@NotNull String string) {
        return string.toLowerCase();
    }
}
