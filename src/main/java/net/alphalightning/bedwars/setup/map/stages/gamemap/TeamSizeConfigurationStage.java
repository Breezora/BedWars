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

        int size = validateInt(event.signedMessage().message());
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
            throw new IllegalStateException("The setup must be a setup of a gamemap!");
        }

        Feedback.success(player);
        player.sendMessage(Component.translatable("mapsetup.stage.3.success"));

        gameMapSetup.configureTeamSize(size);
        gameMapSetup.startStage(4);
    }

    private int validateInt(String message) {
        int integer = 0;
        try {
            integer = Integer.parseInt(message);
        } catch (NumberFormatException exception) {
            Feedback.error(player);
            player.sendMessage(Component.translatable("mapsetup.stage.3.error.invalid-number"));
        }
        return integer;
    }
}
