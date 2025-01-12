package net.alphalightning.bedwars;

import co.aikar.commands.PaperCommandManager;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.retrooper.packetevents.PacketEvents;
import de.eldoria.jacksonbukkit.JacksonPaper;
import net.alphalightning.bedwars.commands.CreateMapCommand;
import net.alphalightning.bedwars.commands.OpenGuiCommand;
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
            .build()
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);

    @Override
    public void onLoad() {
        loadMessageRegistry();
    }

    @Override
    public void onEnable() {
        PacketEvents.getAPI().init();

        registerCommands();
        getLogger().info("BedWars has been enabled");
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
        getLogger().info("BedWars has been disabled");
    }

    private void loadMessageRegistry() {
        translationRegistry.defaultLocale(Locale.GERMAN);
        translationRegistry.registerAll(Locale.GERMAN, ResourceBundle.getBundle("messages", Locale.GERMANY, UTF8ResourceBundleControl.get()), true);
        translationRegistry.registerAll(Locale.US, ResourceBundle.getBundle("messages", Locale.US, UTF8ResourceBundleControl.get()), true);

        GlobalTranslator.translator().addSource(translationRegistry);
    }

    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new CreateMapCommand());
        manager.registerCommand(new OpenGuiCommand()); // Debug command
    }

    public @NotNull ObjectMapper jsonMapper() {
        return mapper;
    }
}
