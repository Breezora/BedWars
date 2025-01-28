package net.alphalightning.bedwars.setup.map.stages.gamemap;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;
import java.util.List;


public class TeamLootspawnerConfigurationStage extends Stage implements LocationConfiguration {

    public TeamLootspawnerConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) { super(plugin, player, setup);
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.count = 0;
            return;
        }
        this.count = gameMapSetup.teams().size();
    }

    private int phase;
    private int slow = 1;
    private final int count;
    private final List<Location> locations = new ArrayList<>();

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.10"));
        startPhase(1);
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (slow == 1) {
            if (isNotPlayerConfiguring(player)) {
                return;
            }
            if (isNotStage(10)) {
                return;
            }

            event.setCancelled(true); //Nachricht nicht in den Chat senden

            String message = event.signedMessage().message();


            if (!message.equalsIgnoreCase("y")) {
                player.sendMessage(Component.translatable("mapsetup.stage.10.setupspeed.error"));
                Feedback.error(player);
                return;
            }
            if (!(setup instanceof GameMapSetup gameMapSetup)) {
                return;
            }
            gameMapSetup.configureSlowIron(true);
            player.sendMessage(Component.translatable("mapsetup.stage.10.setupspeed.slow"));
            Feedback.success(player);
            startPhase(1);
            slow = 2;
        }
    }
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Location location = player.getLocation();

        if (isNotPlayerConfiguring(event.getPlayer())) {
            return;
        }
        if(isNotOnGround(player, location)) {
            return;
        }
        if (isNotStage(10)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }


        if (slow == 1) {
            gameMapSetup.configureSlowIron(false);
            player.sendMessage(Component.translatable("mapsetup.stage.10.setupspeed.fast"));
            Feedback.success(player);
            player.sendMessage(Component.translatable("mapsetup.stage.10.name"));
            startPhase(1);
        }
        if (phase < count) {
            sendSuccessMessage();
            Feedback.success(player);

            locations.add(location);
            startPhase(++phase);
        }
    }
    private void startPhase(int phase) {
        if (phase > count) {
            return;
        }
        this.phase = phase;

        if (slow == 1) {
            player.sendMessage(Component.translatable("mapsetup.stage.10.setupspeed"));
            return;
        }


        player.sendMessage(Component.translatable("mapsetup.stage.10.name", Component.text(phase)));
        Feedback.success(player);
    }

    private void sendSuccessMessage() {
        player.sendMessage(Component.translatable("mapsetup.stage.10.name.success", Component.text(phase)));
    }
}
