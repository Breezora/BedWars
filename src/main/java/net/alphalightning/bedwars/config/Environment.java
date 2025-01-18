package net.alphalightning.bedwars.config;

public enum Environment {

    DEVELOPMENT("development"),
    STAGING("staging"),
    PRODUCTION("production");

    private final String key;

    Environment(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
