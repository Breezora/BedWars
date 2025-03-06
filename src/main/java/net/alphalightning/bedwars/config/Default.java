package net.alphalightning.bedwars.config;

public class Default {

    private Environment environment;
    private double minPlayers;

    public Environment environment() {
        return environment;
    }

    public double minPlayers() {
        return minPlayers;
    }

    public void environment(Environment environment) {
        this.environment = environment;
    }

    public void minPlayers(double minPlayers) {
        this.minPlayers = minPlayers;
    }
}
