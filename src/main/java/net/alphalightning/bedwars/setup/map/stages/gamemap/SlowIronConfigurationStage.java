package net.alphalightning.bedwars.setup.map.stages.gamemap;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SlowIronConfigurationStage extends Stage {

    private static final String YES = "yes";
    private static final String YES_ALIAS = "y";
    private static final String NO = "no";
    private static final String NO_ALIAS = "n";
    private static final Set<String> VALID_MESSAGES = Set.of(YES, YES_ALIAS, NO, NO_ALIAS);

    public SlowIronConfigurationStage(@NotNull BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.10"));
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (isNotPlayerConfiguring(player)) {
            return;
        }
        if (isNotStage(GameMapSetup.SLOW_IRON_CONFIGURATION_STAGE)) {
            return;
        }
        if(!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        event.setCancelled(true);

        String message = event.signedMessage().message();

        if (!VALID_MESSAGES.contains(message.toLowerCase())) {
            player.sendMessage(Component.translatable("mapsetup.stage.10.error"));
            Feedback.error(player);
            return;
        }

        boolean isFast = isSpawnFast(message);
        gameMapSetup.configureSlowIron(isFast);

        if (isFast) {
            player.sendMessage(Component.translatable("mapsetup.stage.10.slow"));
        } else {
            player.sendMessage(Component.translatable("mapsetup.stage.10.fast"));
        }

        Feedback.success(player);
        player.sendMessage(Component.translatable("mapsetup.stage.10.success"));
        gameMapSetup.startStage(GameMapSetup.TEAM_LOOTSPAWNER_CONFIGURATION_STAGE);
    }

    private boolean isSpawnFast(@NotNull String message) {
        return message.equalsIgnoreCase(YES) || message.equalsIgnoreCase(YES_ALIAS);
    }
}
