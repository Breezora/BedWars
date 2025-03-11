package net.alphalightning.bedwars.game.team;

import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TeamFactory {

    public static List<Team> createTeams(@NotNull List<JacksonTeam> jacksonTeams) {
        return jacksonTeams.stream().map(Team::new).toList();
    }

}
