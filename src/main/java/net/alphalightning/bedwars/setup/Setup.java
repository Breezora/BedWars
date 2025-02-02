package net.alphalightning.bedwars.setup;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.GameMapSetupBuilder;
import net.alphalightning.bedwars.setup.map.LobbyMapSetupBuilder;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface Setup {

    void start();

    void finish(int lastStage);

    void cancel();

    void saveConfiguration();

    BedWarsPlugin plugin();

    int stage();

    default Path directory() {
        return plugin().getDataFolder().toPath();
    }

    default Path mapsDirectory() {
        return directory().resolve("maps");
    }

    default void createDirectory() throws IOException {
        Path configDirPath = mapsDirectory();
        if (!Files.exists(configDirPath)) {
            Files.createDirectory(configDirPath);
        }
    }

    static MapSetup.Builder mapBuilder(@NotNull ConfigurationType type) {
        if (type == ConfigurationType.LOBBY) {
            return new LobbyMapSetupBuilder();
        }
        return new GameMapSetupBuilder();
    }

    interface Builder<T> {
        T plugin(BedWarsPlugin plugin);

        T executor(Player player);

        T name(String name);
    }
}