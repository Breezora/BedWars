package net.alphalightning.bedwars.game.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.jackson.GameMap;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class MapManager {

    private final List<GameMap> maps;
    private GameMap selected; // The selected gamemap

    public MapManager(@NotNull BedWarsPlugin plugin) {
        String matchmaking = plugin.configuration().main().matchmaking();

        this.maps = new MapLoader(plugin, matchmaking).loadAll();
        printMatchmakingInfo(plugin, matchmaking);
    }

    public @Nullable GameMap selectRandom() {
        if (this.maps.isEmpty()) {
            return null;
        }

        int index = new Random().nextInt(this.maps.size());
        this.selected = this.maps.get(index);

        updateServerInfo();
        return this.selected;
    }

    public boolean select(String name) {
        this.selected = this.maps.stream()
                .filter(map -> map.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        if (this.selected == null) {
            return false;
        }
        updateServerInfo();
        return true;
    }

    // --------------------- Exposure ---------------------

    public GameMap selected() {
        return selected;
    }

    public List<GameMap> maps() {
        return this.maps;
    }

    // --------------------- Internal logic ---------------------

    private void updateServerInfo() {
        Bukkit.getServer().motd(Component.text(this.selected.name()));
        Bukkit.getServer().setMaxPlayers(this.selected.teams().size() * this.selected.teamSize());
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
