package net.alphalightning.bedwars.config;

public class Skins {

    private Skin team;
    private Skin spectator;

    public Skin team() {
        return team;
    }

    public void team(Skin team) {
        this.team = team;
    }

    public Skin spectator() {
        return spectator;
    }

    public void spectator(Skin spectator) {
        this.spectator = spectator;
    }
}
