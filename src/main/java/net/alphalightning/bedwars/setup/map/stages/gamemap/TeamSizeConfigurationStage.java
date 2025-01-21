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

public class TeamSizeConfigurationStage extends Stage {

    private static final int MIN_TEAM_SIZE = 1;
    private static final int MAX_TEAM_SIZE = 100;

    public TeamSizeConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.3"));
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (player == null || !player.equals(event.getPlayer())) {
            return;
        }
        if (setup.stage() != 3) {
            return;
        }

        event.setCancelled(true); // Do not send the message inti the chat

        int size;
        try {
            size = Integer.parseInt(event.signedMessage().message());
        } catch (NumberFormatException exception) {
            Feedback.error(player);
            player.sendMessage(Component.translatable("mapsetup.stage.3.error.invalid-number"));
            return;
        }

        if (size < MIN_TEAM_SIZE) {
            Feedback.error(player);
            player.sendMessage(Component.translatable("mapsetup.stage.3.error.negative-or-zero"));
            return;
        }
        if (size > MAX_TEAM_SIZE) {
            Feedback.error(player);
            player.sendMessage(Component.translatable("mapsetup.stage.3.error.too-big"));
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        Feedback.success(player);
        player.sendMessage(Component.translatable("mapsetup.stage.3.success"));

        gameMapSetup.configureTeamSize(size);
        gameMapSetup.startStage(4);
    }
}
