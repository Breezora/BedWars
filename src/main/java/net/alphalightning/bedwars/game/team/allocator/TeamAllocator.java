package net.alphalightning.bedwars.game.team.allocator;

import net.alphalightning.bedwars.game.team.Team;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TeamAllocator {

    /**
     * Teilt die Spieler in Teams anhand der optimalen Konfiguration auf.
     *
     * @param players    Liste der Spieler.
     * @param maxTeams   Maximale Anzahl an Teams.
     * @param maxTeamSize Maximale Spieleranzahl pro Team.
     * @return Liste der Teams mit zugeordneten Spielern.
     */
    @NotNull List<Team> allocateTeams(@NotNull List<Player> players, int maxTeams, int maxTeamSize);

}
