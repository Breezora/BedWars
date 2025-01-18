package net.alphalightning.bedwars;

import co.aikar.commands.PaperCommandManager;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.eldoria.eldoutilities.config.ConfigKey;
import de.eldoria.jacksonbukkit.JacksonPaper;
import net.alphalightning.bedwars.commands.CreateMapCommand;
import net.alphalightning.bedwars.commands.OpenGuiCommand;
import net.alphalightning.bedwars.config.Configuration;
import net.alphalightning.bedwars.config.Default;
import net.alphalightning.bedwars.config.Environment;
import net.alphalightning.bedwars.setup.ui.item.BackgroundGuiItem;
import net.alphalightning.bedwars.translation.PluginTranslationRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.invui.gui.Structure;

import java.util.Locale;
import java.util.ResourceBundle;

public class BedWarsPlugin extends JavaPlugin {

    private final TranslationRegistry translationRegistry = new PluginTranslationRegistry(TranslationRegistry.create(Key.key("bedwars:messages")));
    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(JacksonPaper.builder().build())
            .enable(SerializationFeature.INDENT_OUTPUT) // Pretty printing
            .build()
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);

    private Configuration configuration;
    private Environment environment;

    @Override
    public void onLoad() {
        loadMessageRegistry();
        createOrLoadConfiguration();
    }

    @Override
    public void onEnable() {
        registerCommands();
        registerGuiIngredients();
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

        if (environment != Environment.PRODUCTION) {
            manager.registerCommand(new CreateMapCommand());
            manager.registerCommand(new OpenGuiCommand()); // This is a debug only command. Will be removed after testing
        }
    }

    private void registerGuiIngredients() {
        Structure.addGlobalIngredient('.', new BackgroundGuiItem(false));
        Structure.addGlobalIngredient('#', new BackgroundGuiItem(true));
    }

    private void createOrLoadConfiguration() {
        configuration = new Configuration(this, jsonMapper());

        if (!configuration.exists(ConfigKey.defaultConfig(Default.class, Default::new))) {
            configuration.createDefault();
        }

        environment = configuration.main().environment();
        getLogger().info("Using environment " + environment);
    }

    public ObjectMapper jsonMapper() {
        return mapper;
    }

    public Configuration configuration() {
        return configuration;
    }
}
