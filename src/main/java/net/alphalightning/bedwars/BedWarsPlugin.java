package net.alphalightning.bedwars;

import co.aikar.commands.PaperCommandManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.eldoria.jacksonbukkit.JacksonPaper;
import net.alphalightning.bedwars.commands.CreateMapCommand;
import net.alphalightning.bedwars.commands.SetSpawnPointCommand;
import net.alphalightning.bedwars.translation.PluginTranslationRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class BedWarsPlugin extends JavaPlugin {

    private final TranslationRegistry translationRegistry = new PluginTranslationRegistry(TranslationRegistry.create(Key.key("bedwars:messages")));
    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(JacksonPaper.builder().build())
            .enable(SerializationFeature.INDENT_OUTPUT) // Pretty printing
            .build();

    @Override
    public void onLoad() {
        loadMessageRegistry();
    }

    @Override
    public void onEnable() {
        registerCommands();
        getLogger().info("BedWars has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("BedWars has been disabled");
    }

    private void loadMessageRegistry() {
        translationRegistry.defaultLocale(Locale.GERMAN);
        translationRegistry.registerAll(Locale.GERMAN, ResourceBundle.getBundle("messages", Locale.GERMANY, UTF8ResourceBundleControl.get()), true);

        GlobalTranslator.translator().addSource(translationRegistry);
    }

    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerDependency(BedWarsPlugin.class, this);

        manager.registerCommand(new SetSpawnPointCommand());
        manager.registerCommand(new CreateMapCommand());
    }

    public @NotNull ObjectMapper jsonMapper() {
        return mapper;
    }
}
