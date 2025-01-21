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

import java.time.temporal.ValueRange;

public class BuildHeightConfigurationStage extends Stage {

    private static final int MIN_BUILD_HEIGHT = -64;
    private static final int MAX_BUILD_HEIGHT = 319;

    public BuildHeightConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.4"));
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (player == null || !player.equals(event.getPlayer())) {
            return;
        }
        if (setup.stage() != 4) {
            return;
        }

        event.setCancelled(true); // Do not send the message inti the chat

        int buildHeight;
        try {
            buildHeight = Integer.parseInt(event.signedMessage().message());

        } catch (NumberFormatException exception) {
            player.sendMessage(Component.translatable("mapsetup.stage.4.error.invalid-number"));
            Feedback.error(player);
            return;
        }

        ValueRange range = ValueRange.of(MIN_BUILD_HEIGHT, MAX_BUILD_HEIGHT);
        if (!range.isValidIntValue(buildHeight)) {
            player.sendMessage(Component.translatable("mapsetup.stage.4.error.invalid-range",
                    Component.text(MIN_BUILD_HEIGHT),
                    Component.text(MAX_BUILD_HEIGHT))
            );
            Feedback.error(player);
            return;
        }

        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        player.sendMessage(Component.translatable("mapsetup.stage.4.success", Component.text(buildHeight)));
        Feedback.success(player);

        gameMapSetup.configureBuildHeight(buildHeight);
    }
}
