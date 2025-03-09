package net.alphalightning.bedwars;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.eldoria.jacksonbukkit.JacksonPaper;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.alphalightning.bedwars.commands.CreateMapCommand;
import net.alphalightning.bedwars.commands.ForceMapCommand;
import net.alphalightning.bedwars.commands.TestGuiCommand;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommandSource;
import net.alphalightning.bedwars.commands.cloud.sender.PaperPlayerCommandSource;
import net.alphalightning.bedwars.config.Configuration;
import net.alphalightning.bedwars.config.Environment;
import net.alphalightning.bedwars.game.listener.BlockListener;
import net.alphalightning.bedwars.game.listener.FoodLevelListener;
import net.alphalightning.bedwars.game.state.GameState;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.alphalightning.bedwars.setup.manager.MapSetupManager;
import net.alphalightning.bedwars.setup.ui.item.BackgroundGuiItem;
import net.alphalightning.bedwars.translation.PluginMiniMassageTranslator;
import net.alphalightning.bedwars.util.WorldUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.caption.ComponentCaptionFormatter;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Structure;

import java.util.Locale;
import java.util.ResourceBundle;

public class BedWarsPlugin extends JavaPlugin {

    private final MapSetupManager setupManager = MapSetupManager.instance();
    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(JacksonPaper.builder().build())
            .enable(SerializationFeature.INDENT_OUTPUT) // Pretty printing
            .build()
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);

    private Configuration configuration;
    private Environment environment;
    private GameStateContext gameStateContext;

    @Override
    public void onLoad() {
        loadMessageRegistry();
        loadConfiguration();
    }

    @Override
    public void onEnable() {
        registerEvents();
        registerCommands();
        registerGuiIngredients();
        registerGameMechanics();

        getLogger().info("BedWars has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("BedWars has been disabled");
    }

    // --------------------- Initialization ---------------------

    private void loadMessageRegistry() {
        TranslationRegistry translationRegistry = TranslationRegistry.create(Key.key("bedwars:messages"));
        translationRegistry.defaultLocale(Locale.GERMAN);
        translationRegistry.registerAll(Locale.GERMAN, ResourceBundle.getBundle("messages", Locale.GERMANY, UTF8ResourceBundleControl.get()), true);

        GlobalTranslator.translator().addSource(translationRegistry);
        GlobalTranslator.translator().addSource(new PluginMiniMassageTranslator(translationRegistry));
    }

    private void loadConfiguration() {
        configuration = new Configuration(this, mapper);
        configuration.createOrDoNothing();

        environment = configuration.main().environment();
        getComponentLogger().info(MiniMessage.miniMessage().deserialize("Using the environment " + Environment.colored(environment)));
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new FoodLevelListener(), this);
        pluginManager.registerEvents(new BlockListener(this), this);
    }

    private void registerCommands() {
        PaperCommandManager<PaperCommandSource> manager = PaperCommandManager.builder(senderMapper())
                .executionCoordinator(ExecutionCoordinator.<PaperCommandSource>builder().build())
                .buildOnEnable(this);
        MinecraftExceptionHandler.<PaperCommandSource>createNative()
                .defaultHandlers()
                .captionFormatter(ComponentCaptionFormatter.miniMessage())
                .registerTo(manager);

        if (environment != Environment.DEVELOPMENT) {
            new ForceMapCommand(this).register(manager);
        }
        if (environment != Environment.PRODUCTION) {
            new TestGuiCommand(this).register(manager);
            new CreateMapCommand(this, setupManager).register(manager);

            getComponentLogger().info(MiniMessage.miniMessage().deserialize("<green>Enabled <reset>map creation"));
            return;
        }

        getComponentLogger().info(MiniMessage.miniMessage().deserialize("<red>Disabled <reset>map creation"));
    }

    private @NotNull SenderMapper<CommandSourceStack, PaperCommandSource> senderMapper() {
        return SenderMapper.create(commandSourceStack -> {
            CommandSender sender = commandSourceStack.getSender();

            return sender instanceof Player player ?
                    new PaperPlayerCommandSource(player, commandSourceStack) :
                    new PaperCommandSource(sender, commandSourceStack);

        }, PaperCommandSource::commandSourceStack);
    }

    private void registerGuiIngredients() {
        Structure.addGlobalIngredient('.', new BackgroundGuiItem(false));
        Structure.addGlobalIngredient('#', new BackgroundGuiItem(true));
    }

    private void registerGameMechanics() {
        if (environment == Environment.DEVELOPMENT) {
            getComponentLogger().info(MiniMessage.miniMessage().deserialize("<red>Disabled <reset>game mechanics!"));
            return;
        }
        WorldUtil.prepareWorlds(Bukkit.getWorlds());

        gameStateContext = new GameStateContext(this);
        gameStateContext.setGameState(GameState.LOBBY);
    }

    // --------------------- Exposure---------------------

    public ObjectMapper jsonMapper() {
        return mapper;
    }

    public Configuration configuration() {
        return configuration;
    }

    public MapSetupManager setupManager() {
        return setupManager;
    }

    public GameStateContext gameStateContext() {
        return gameStateContext;
    }
}
