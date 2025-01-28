package net.alphalightning.bedwars;

import co.aikar.commands.PaperCommandManager;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.eldoria.jacksonbukkit.JacksonPaper;
import net.alphalightning.bedwars.commands.CreateMapCommand;
import net.alphalightning.bedwars.config.Configuration;
import net.alphalightning.bedwars.config.Environment;
import net.alphalightning.bedwars.setup.ui.item.BackgroundGuiItem;
import net.alphalightning.bedwars.translation.PluginMiniMassageTranslator;
import net.alphalightning.bedwars.translation.PluginTranslationRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.invui.gui.Structure;

import java.util.Locale;
import java.util.ResourceBundle;

public class BedWarsPlugin extends JavaPlugin {

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
        loadConfiguration();
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
        TranslationRegistry translationRegistry = new PluginTranslationRegistry(TranslationRegistry.create(Key.key("bedwars:messages")));

        translationRegistry.defaultLocale(Locale.GERMAN);
        translationRegistry.registerAll(Locale.GERMAN, ResourceBundle.getBundle("messages", Locale.GERMANY, UTF8ResourceBundleControl.get()), true);

        GlobalTranslator.translator().addSource(translationRegistry);
        GlobalTranslator.translator().addSource(new PluginMiniMassageTranslator(translationRegistry));
    }

    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);

        if (environment != Environment.PRODUCTION) {
            manager.registerCommand(new CreateMapCommand()); // Command to create a new map

            getComponentLogger().info(MiniMessage.miniMessage().deserialize("<green>Enabled <reset>map creation"));
            return;
        }
        getComponentLogger().info(MiniMessage.miniMessage().deserialize("<red>Disabled <reset>map creation"));
    }

    private void registerGuiIngredients() {
        Structure.addGlobalIngredient('.', new BackgroundGuiItem(false));
        Structure.addGlobalIngredient('#', new BackgroundGuiItem(true));
    }

    private void loadConfiguration() {
        configuration = new Configuration(this, mapper);
        configuration.createOrDoNothing();

        environment = configuration.main().environment();
        getComponentLogger().info(MiniMessage.miniMessage().deserialize("Using the environment " + Environment.colored(environment)));
    }

    public ObjectMapper jsonMapper() {
        return mapper;
    }

    public Configuration configuration() {
        return configuration;
    }
}
