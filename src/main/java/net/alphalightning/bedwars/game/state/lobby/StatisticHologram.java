package net.alphalightning.bedwars.game.state.lobby;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.state.states.LobbyState;
import net.alphalightning.bedwars.util.MathUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatisticHologram {

    private final List<Entity> displays = new ArrayList<>();
    private final BedWarsPlugin plugin;
    private final LobbyState lobbyState;
    private final Player player;

    public StatisticHologram(BedWarsPlugin plugin, LobbyState lobbyState, Player player) {
        this.plugin = plugin;
        this.lobbyState = lobbyState;
        this.player = player;
    }

    public StatisticHologram spawn() {
        final Location location = this.lobbyState.hologramLocation();

        if (location == null) {
            this.plugin.getComponentLogger().warn(Component.translatable("state.lobby.hologram"));
            return null;
        }

        // Values and randoms are dummy data and have to be replaced witch actual statistics when they're implemented
        int kills = randomInt();
        int playedGames = randomInt();
        int deaths;
        do {
            deaths = randomInt();
        } while (deaths > playedGames);
        int wonGames = playedGames - deaths;

        createLine(location, 2.25, Component.translatable("hologram.name"));
        createLine(location, 2, Component.translatable("hologram.all-time"));
        createLine(location, 1.75, Component.translatable("hologram.ranking", Component.text(randomInt())));
        createLine(location, 1.5, Component.translatable("hologram.kills", Component.text(kills)));
        createLine(location, 1.25, Component.translatable("hologram.deaths", Component.text(deaths)));
        createLine(location, 1, Component.translatable("hologram.kd", Component.text(MathUtil.round((double) kills / deaths, 2))));
        createLine(location, 0.75, Component.translatable("hologram.played-games", Component.text(playedGames)));
        createLine(location, 0.5, Component.translatable("hologram.wins", Component.text(wonGames)));
        createLine(location, 0.25, Component.translatable("hologram.destroyed-beds", Component.text(randomInt())));
        createLine(location, 0, Component.translatable("hologram.win-rate", Component.text(MathUtil.toPercentage((double) wonGames / playedGames, 2))));

        return this;
    }

    public void destroy() {
        this.displays.forEach(Entity::remove);
    }

    private void createLine(@NotNull Location location, double offset, Component component) {
        this.displays.add(location.getWorld().spawnEntity(location.clone().add(0D, offset, 0D), EntityType.TEXT_DISPLAY, SpawnReason.CUSTOM, entity -> {
            final TextDisplay textDisplay = (TextDisplay) entity;
            textDisplay.text(component);
            textDisplay.setShadowed(true);
            textDisplay.setSeeThrough(false);

            hide(textDisplay);
        }));
    }

    private void hide(Entity entity) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> !this.player.equals(player))
                .findAny()
                .ifPresent(player -> player.hideEntity(this.plugin, entity));
    }

    private int randomInt() {
        return new Random().nextInt(100);
    }

}
