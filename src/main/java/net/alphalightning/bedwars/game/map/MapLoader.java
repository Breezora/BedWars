package net.alphalightning.bedwars.game.map;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.jackson.GameMap;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MapLoader {

    private final BedWarsPlugin plugin;
    private final String matchmaking;

    public MapLoader(BedWarsPlugin plugin, String matchmaking) {
        this.plugin = plugin;
        this.matchmaking = matchmaking;
    }

    public Collection<GameMap> loadAll() {
        File directory = this.plugin.getDataFolder().toPath().resolve("maps").toFile();
        File[] files = directory.listFiles((_, name) -> {
            String[] parts = name.split("\\.");
            return parts[1].equalsIgnoreCase("json"); // Find only json files
        });

        if (files == null) {
            return Collections.emptyList();
        }

        Collection<GameMap> maps = new ArrayList<>();
        File tmpFile = null;
        try {
            for (File file : files) {
                tmpFile = file;
                GameMap gameMap = this.plugin.jsonMapper().readValue(file, GameMap.class);

                if (isValidMatchmaking(gameMap)) {
                    maps.add(gameMap);
                }
            }
            return maps;

        } catch (IOException exception) {
            this.plugin.getLogger().severe("Could not read file " + tmpFile.getName() + ": " + exception.getMessage());
        }

        return Collections.emptyList();
    }

    private boolean isValidMatchmaking(@NotNull GameMap gameMap) {
        int teamSize = gameMap.teamSize();
        int teams = gameMap.teams().size();
        return matchmaking.equalsIgnoreCase(teams + "x" + teamSize);
    }
}
