package net.alphalightning.bedwars.setup.map.stages;

import org.jetbrains.annotations.NotNull;

public interface TeamConfiguration {

    String LEGACY_CONVENTION = "_";
    String PROPERTIES_CONVENTION = "-";

    default @NotNull String convertName(@NotNull String name) {
        if (!name.contains(LEGACY_CONVENTION)) {
            return name;
        }
        return name.replace(LEGACY_CONVENTION, PROPERTIES_CONVENTION);
    }

}
