package net.alphalightning.bedwars.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.eldoria.eldoutilities.config.ConfigKey;
import de.eldoria.eldoutilities.config.JacksonConfig;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Configuration extends JacksonConfig<Default> {

    private final ObjectMapper mapper;

    public Configuration(@NotNull Plugin plugin, ConfigKey<Default> mainKey, ObjectMapper mapper) {
        super(plugin, mainKey);
        this.mapper = mapper;
    }

    @Override
    protected ObjectMapper createMapper() {
        return mapper;
    }

    public void createDefault() {
        main().environment(Environment.DEVELOPMENT);
        save();
    }
}
