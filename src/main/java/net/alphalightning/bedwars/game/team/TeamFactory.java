package net.alphalightning.bedwars.game.team;

import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TeamFactory {

    public static @NotNull List<Team> createTeams(@NotNull List<JacksonTeam> jacksonTeams) {
        List<Team> list = new ArrayList<>();
        for (JacksonTeam jacksonTeam : jacksonTeams) {
            Team team = new Team(jacksonTeam);
            list.add(team);
        }
        return list;
    }

}
