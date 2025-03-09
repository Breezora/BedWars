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
            world.setTime(6000L);
            world.setDifficulty(Difficulty.PEACEFUL);
            flag(world, List.of(
                    GameRule.DO_MOB_LOOT,
                    GameRule.DO_MOB_SPAWNING,
                    GameRule.DO_WEATHER_CYCLE,
                    GameRule.DO_DAYLIGHT_CYCLE,
                    GameRule.ANNOUNCE_ADVANCEMENTS,
                    GameRule.DO_FIRE_TICK,
                    GameRule.WATER_SOURCE_CONVERSION
            ));
        }
    }

    private static void flag(World world, @NotNull List<GameRule<Boolean>> rules) {
        rules.forEach(rule -> world.setGameRule(rule, false));
    }

}
