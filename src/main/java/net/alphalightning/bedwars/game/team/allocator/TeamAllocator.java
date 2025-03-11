package net.alphalightning.bedwars.game.team.allocator;

import net.alphalightning.bedwars.game.team.Team;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TeamAllocator {

    @NotNull List<Team> allocateTeams(@NotNull List<Player> players, int maxTeams, int maxTeamSize);

}
