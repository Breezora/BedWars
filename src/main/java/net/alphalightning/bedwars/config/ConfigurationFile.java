package net.alphalightning.bedwars.config;

import de.eldoria.eldoutilities.config.ConfigKey;
import de.eldoria.eldoutilities.config.JacksonConfig;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ConfigurationFile extends JacksonConfig<General> {

    private static final ConfigKey<General> MAIN = ConfigKey.defaultConfig(General.class, () -> new General(Environment.DEVELOPMENT));

    public ConfigurationFile(@NotNull Plugin plugin) {
        super(plugin, MAIN);
    }
}
