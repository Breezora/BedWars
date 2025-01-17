package net.alphalightning.bedwars.config;

public enum Environment {

    PRODUCTION("production"),
    STAGING("staging"),
    DEVELOPMENT("development");

    private final String key;

    Environment(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
