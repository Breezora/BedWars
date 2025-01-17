package net.alphalightning.bedwars;

import co.aikar.commands.PaperCommandManager;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.eldoria.jacksonbukkit.JacksonPaper;
import net.alphalightning.bedwars.commands.CreateMapCommand;
import net.alphalightning.bedwars.commands.OpenGuiCommand;
import net.alphalightning.bedwars.config.ConfigurationFile;
import net.alphalightning.bedwars.setup.ui.item.BackgroundGuiItem;
import net.alphalightning.bedwars.translation.PluginTranslationRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
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

    private ConfigurationFile configuration;

    @Override
    public void onLoad() {
        loadMessageRegistry();
    }

    @Override
    public void onEnable() {
        loadConfiguration();
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

        manager.registerCommand(new CreateMapCommand());
        manager.registerCommand(new OpenGuiCommand()); // Debug command
    }

    private void registerGuiIngredients() {
        Structure.addGlobalIngredient('.', new BackgroundGuiItem(false));
        Structure.addGlobalIngredient('#', new BackgroundGuiItem(true));
    }

    private void loadConfiguration() {
        configuration = new ConfigurationFile(this);
        configuration.configureDefault(JsonMapper.builder());
        configuration.save();
    }

    public @NotNull ObjectMapper jsonMapper() {
        return mapper;
    }

    public @NotNull ConfigurationFile configuration() {
        return configuration;
    }
}
