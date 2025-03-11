package net.alphalightning.bedwars.game.team.allocator;

import net.alphalightning.bedwars.game.team.Team;
import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class DynamicTeamAllocatorTest {

    private ServerMock server;

    @BeforeEach
    void setUp() {
        this.server = MockBukkit.mock();
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    private @NotNull List<JacksonTeam> createTeamMocks(int count) {
        List<JacksonTeam> teams = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            teams.add(mock(JacksonTeam.class));
        }
        return teams;
    }

    private @NotNull List<PlayerMock> createPlayerMocks(int count) {
        List<PlayerMock> list = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            PlayerMock playerMock = this.server.addPlayer();
            list.add(playerMock);
        }
        return list;
    }

    @Test
    void testEvenTeamDistribution() {
        List<PlayerMock> players = createPlayerMocks(8);
        int maxTeams = 4;
        int maxTeamSize = 2;

        TeamAllocator allocator = new DynamicTeamAllocator(createTeamMocks(maxTeams));
        List<Team> teams = allocator.allocateTeams(players, maxTeams, maxTeamSize);
        int totalPlayers = teams.stream().mapToInt(team -> team.players().size()).sum();

        assertEquals(8, totalPlayers, "Alle 8 Spieler müssen verteilt werden.");
        teams.forEach(team -> assertTrue(team.players().size() <= maxTeamSize, "Kein Team darf mehr als zwei Spieler haben."));
    }

    @ParameterizedTest
    @CsvSource({
            "9, 3, 4", // 9 Spieler auf 3 Teams mit max. 4 Spielern pro Team
            "11, 4, 3", // 11 Spieler auf 4 Teams mit max. 3 Spielern pro Team
            "13, 5, 3" // 13 Spieler auf 5 Teams mit max. 3 Spielern pro Team
    })
    void testUnevenPlayerCounts(int playerCount, int maxTeams, int maxTeamSize) {
        List<PlayerMock> players = createPlayerMocks(playerCount);

        TeamAllocator allocator = new DynamicTeamAllocator(createTeamMocks(maxTeams));
        List<Team> teams = allocator.allocateTeams(players, maxTeams, maxTeamSize);

        // Prüfen, dass alle Spieler einem Team zugewiesen wurden
        int totalPlayers = teams.stream().mapToInt(team -> team.players().size()).sum();
        assertEquals(playerCount, totalPlayers, "Alle Spieler müssen korrekt zugewiesen werden.");

        // Prüfen, dass der Unterschied zwischen größten und kleinsten Teams höchstens 1 beträgt
        int minSize = teams.stream().mapToInt(team -> team.players().size()).filter(value -> value > 0).min().orElse(0);
        int maxSize = teams.stream().mapToInt(team -> team.players().size()).filter(value -> value > 0).max().orElse(0);
        assertTrue(maxSize - minSize <= 1, "Der Unterschied zwischen Teamgrößen darf maximal 1 sein.");
    }

    @Test
    void testPerfectMatch() {
        List<PlayerMock> players = createPlayerMocks(12);
        int maxTeams = 4;
        int maxTeamSize = 3;

        TeamAllocator allocator = new DynamicTeamAllocator(createTeamMocks(maxTeams));
        List<Team> teams = allocator.allocateTeams(players, maxTeams, maxTeamSize);
        teams.forEach(team -> assertEquals(3, team.players().size(), "Jedes Team sollte genau 3 Spieler haben."));
    }

    @ParameterizedTest
    @ValueSource(ints = {50, 75, 100})
    void testHighPlayerCounts(int playerCount) {
        List<PlayerMock> players = createPlayerMocks(playerCount);
        int maxTeams = 16;
        int maxTeamSize = 10;

        TeamAllocator allocator = new DynamicTeamAllocator(createTeamMocks(maxTeams));
        List<Team> teams = allocator.allocateTeams(players, maxTeams, maxTeamSize);

        int totalPlayers = teams.stream().mapToInt(team -> team.players().size()).sum();
        assertEquals(playerCount, totalPlayers, "Alle Spieler müssen korrekt zugewiesen werden.");
    }
}