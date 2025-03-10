package net.alphalightning.bedwars.config;

public class Default {

    private Environment environment;
    private String matchmaking;
    private double minPlayers;
    private double forceStartFactor;

    public Environment environment() {
        return environment;
    }


    public String matchmaking() {
        return matchmaking;
    }

    public double minPlayers() {
        return minPlayers;
    }

    public double forceStartFactor() {
        return forceStartFactor;
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

    public void forceStartFactor(double forceStartFactor) {
        this.forceStartFactor = forceStartFactor;
    }
}
