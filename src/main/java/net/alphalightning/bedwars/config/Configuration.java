package net.alphalightning.bedwars.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.eldoria.eldoutilities.config.ConfigKey;
import de.eldoria.eldoutilities.config.JacksonConfig;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class Configuration extends JacksonConfig<Default> {

    private static final ConfigKey<Default> MAIN_KEY = ConfigKey.of("config", Path.of("config.json"), Default.class, Default::new);

    private final ObjectMapper mapper;

    public Configuration(@NotNull Plugin plugin, ObjectMapper mapper) {
        super(plugin, MAIN_KEY);
        this.mapper = mapper;
    }

    @Override
    protected ObjectMapper createMapper() {
        return mapper;
    }

    public void createOrDoNothing() {
        if (exists(MAIN_KEY)) {
            return;
        }

        main().environment(Environment.DEVELOPMENT);
        save();
    }
}
