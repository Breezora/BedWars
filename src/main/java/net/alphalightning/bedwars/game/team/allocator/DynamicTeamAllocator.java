package net.alphalightning.bedwars.game.team.allocator;

import net.alphalightning.bedwars.game.team.Team;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class DynamicTeamAllocator implements TeamAllocator {

    @Override
    public @NotNull List<Team> allocateTeams(@NotNull List<Player> players, int maxTeams, int maxTeamSize) {
        Collections.shuffle(players);
        TeamConfig config = calculateOptimalConfig(players.size(), maxTeams, maxTeamSize);

        return List.of();
    }

    /**
     * Berechnet basierend auf der Gesamtzahl der Spieler, der maximalen Teamanzahl und der maximalen Teamgröße
     * die optimale Konfiguration (Anzahl der Teams und benötigte Spieler pro Team).
     * Das Auswahlkriterium ist, dass die Teams so groß wie möglich werden und gleichmäßig aufgeteilt sind.
     *
     * @param totalPlayers Gesamtzahl der verfügbaren Spieler.
     * @param maxTeams     Maximale Anzahl an Teams.
     * @param maxTeamSize  Maximale Anzahl an Spielern pro Team.
     * @return Optimale Teamkonfiguration.
     */
    private @NotNull TeamConfig calculateOptimalConfig(int totalPlayers, int maxTeams, int maxTeamSize) {
        TeamConfig bestConfig = null;

        for (int count = 1; count <= maxTeams; count++) {
            int teamSize = (int) Math.ceil((double) totalPlayers / count);

            // If calculated team size is bigger than the actual max size we want to skip this calculation
            if (teamSize > maxTeamSize) {
                continue;
            }

            TeamConfig config = new TeamConfig(count, teamSize);

            // If no best config exists or a better one was found update it
            if (bestConfig == null || isBetterConfig(config, bestConfig)) {
                bestConfig = config;
            }
        }

        // Fallback: No suitable config was found -> using standard the config
        return bestConfig != null ? bestConfig : new TeamConfig(maxTeams, maxTeamSize);
    }

    /**
     * Vergleicht zwei Teamkonfigurationen und entscheidet, ob die neue Konfiguration besser ist.
     * Eine Konfiguration wird als besser betrachtet, wenn:
     * <ul>
     *     <li>Sie eine größere Teamgröße hat</li>
     *     <li>Bei gleicher Teamgröße eine geringere Anzahl an Teams verwendet wird</li>
     * </ul>
     *
     * @param current Die zu prüfende neue Teamkonfiguration.
     * @param best    Die bisher beste gefundene Teamkonfiguration.
     * @return true, wenn die neue Konfiguration besser ist, sonst false.
     */
    private boolean isBetterConfig(@NotNull TeamConfig current, @NotNull TeamConfig best) {
        return current.teamSize > best.teamSize || (current.teamSize == best.teamSize && current.teamCount < best.teamCount);
    }

    /**
     * Hilfsklasse zur Speicherung der Teamkonfiguration.
     */
    private record TeamConfig(int teamCount, int teamSize) {
    }
}
