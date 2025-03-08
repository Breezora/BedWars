package net.alphalightning.bedwars.game.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.jackson.GameMap;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class MapManager {

    private final List<GameMap> maps;

    public MapManager(@NotNull BedWarsPlugin plugin) {
        String matchmaking = plugin.configuration().main().matchmaking();

        this.maps = new MapLoader(plugin, matchmaking).loadAll();
        printMatchmakingInfo(plugin, matchmaking);
    }

    public @NotNull GameMap selectRandom() {
        int index = new Random().nextInt(this.maps.size());
        return this.maps.get(index);
    }

    private void printMatchmakingInfo(BedWarsPlugin plugin, String matchmaking) {
        if (this.maps.isEmpty()) {
            plugin.getComponentLogger().warn(Component.translatable("state.lobby.matchmaking.error",
                    NamedTranslationArgument.component("matchmaking", Component.text(matchmaking)))
            );
            return;
        }
        plugin.getComponentLogger().info(Component.translatable("state.lobby.matchmaking",
                NamedTranslationArgument.numeric("size", this.maps.size()),
                NamedTranslationArgument.component("matchmaking", Component.text(matchmaking)))
        );
    }
}
