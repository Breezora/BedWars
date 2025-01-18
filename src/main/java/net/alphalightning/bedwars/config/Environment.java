package net.alphalightning.bedwars.config;

public enum Environment {

    DEVELOPMENT, STAGING, PRODUCTION;

    public static String colored(Environment environment) {
        return switch (environment) {
            case DEVELOPMENT -> "<aqua>";
            case STAGING -> "yellow>";
            case PRODUCTION -> "<green>";

        } + environment.name();
    }

}
