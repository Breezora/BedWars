package net.alphalightning.bedwars.util;

import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class WorldUtil {

    public static void prepareWorlds(@NotNull List<World> worlds) {
        for (World world : worlds) {
            world.getEntities().forEach(Entity::remove);
            world.setAutoSave(false);
            world.setThundering(false);
            world.setTime(0L);
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setGameRule(GameRule.DO_MOB_LOOT, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }
    }

}
