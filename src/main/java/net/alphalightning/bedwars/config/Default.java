package net.alphalightning.bedwars.config;

public class Default {

    private Environment environment;
    private String matchmaking;
    private double minPlayers;

    public Environment environment() {
        return environment;
    }

    public double minPlayers() {
        return minPlayers;
    }

    public String matchmaking() {
        return matchmaking;
    }

    public void environment(Environment environment) {
        this.environment = environment;
    }

    public void minPlayers(double minPlayers) {
        this.minPlayers = minPlayers;
    }

    public void matchmaking(String matchmaking) {
        this.matchmaking = matchmaking;
    }
}
