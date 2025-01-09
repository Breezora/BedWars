package net.alphalightning.bedwars.setup;

import org.bukkit.entity.Player;

public interface Setup {

    void start();

    void finish();

    void saveConfiguration();

    interface Builder<T> {
        T executor(Player player);

        T type(ConfigurationType type);

        T name(String name);
    }
}