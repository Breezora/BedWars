package net.alphalightning.bedwars.config;

import de.eldoria.eldoutilities.config.ConfigKey;
import de.eldoria.eldoutilities.config.JacksonConfig;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Configuration extends JacksonConfig<Default> {

    private static final ConfigKey<Default> MAIN = ConfigKey.defaultConfig(Default.class, Default::new);

    public Configuration(@NotNull Plugin plugin) {
        super(plugin, MAIN);
    }

    public void createDefault() {
        main().environment(Environment.DEVELOPMENT);
        save();
    }
}
