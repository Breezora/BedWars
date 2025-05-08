package net.alphalightning.bedwars.game.team;

import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private final List<Player> players = new ArrayList<>();
    private final JacksonTeam backed;

    public Team(JacksonTeam backedTeam) {
        this.backed = backedTeam;
    }

    public void addPlayer(@NotNull Player player) {
        this.players.add(player);
    }

    public @NotNull String name() {
        return this.backed.name();
    }

    public @NotNull List<Player> players() {
        return this.players;
    }

    public Location spawnpoint() {
       return this.backed.spawnpoint().asBukkitLocation();
    }
    public int color() {
        return this.backed.color();
    }
    @Override
    public String toString() {
        return "Team{" +
                "players=" + players +
                ", backed=" + backed +
                '}';
    }
}
