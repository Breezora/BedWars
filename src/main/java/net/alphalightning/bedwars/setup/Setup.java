package net.alphalightning.bedwars.setup;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.GameMapSetupBuilder;
import net.alphalightning.bedwars.setup.map.LobbyMapSetupBuilder;
import net.alphalightning.bedwars.setup.map.MapSetup;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Setup {

    void start();

    void finish();

    void saveConfiguration();

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