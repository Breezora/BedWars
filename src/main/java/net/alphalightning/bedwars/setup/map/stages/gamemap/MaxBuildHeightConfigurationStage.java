package net.alphalightning.bedwars.setup.map.stages.gamemap;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.HeightConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.feedback.visual.impl.HeightRenderer;
import net.alphalightning.bedwars.feedback.visual.impl.HeightVisualization;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.time.temporal.ValueRange;

public class MaxBuildHeightConfigurationStage extends Stage implements HeightConfiguration {

    public MaxBuildHeightConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.4"));
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (isNotPlayerConfiguring(event.getPlayer())) {
            return;
        }
        if (isNotStage(4)) {
            return;
        }

        event.setCancelled(true); // Do not send the message into the chat

        int buildHeight;
        try {
            buildHeight = Integer.parseInt(event.signedMessage().message());

        } catch (NumberFormatException exception) {
            player.sendMessage(Component.translatable("mapsetup.stage.4.error.invalid-number"));
            Feedback.error(player);
            return;
        }

        ValueRange range = ValueRange.of(MIN_MAX_HEIGHT, MAX_HEIGHT);
        if (!range.isValidIntValue(buildHeight)) {
            player.sendMessage(Component.translatable("mapsetup.stage.4.error.invalid-range",
                    Component.text(MIN_MAX_HEIGHT),
                    Component.text(MAX_HEIGHT))
            );
            Feedback.error(player);
            return;
        }

        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        new HeightRenderer(plugin, player).render(new HeightVisualization(buildHeight));
        player.sendMessage(Component.translatable("mapsetup.stage.4.success", Component.text(buildHeight)));
        Feedback.success(player);

        gameMapSetup.configureMaxBuildHeight(buildHeight);
        gameMapSetup.startStage(5);
    }
}
