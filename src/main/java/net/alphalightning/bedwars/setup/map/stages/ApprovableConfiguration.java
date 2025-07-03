package net.alphalightning.bedwars.setup.map.stages;

import java.util.Set;

public interface ApprovableConfiguration {

    String YES = "yes";
    String YES_ALIAS = "y";
    String NO = "no";
    String NO_ALIAS = "n";
    Set<String> VALID_MESSAGES = Set.of(YES, YES_ALIAS, NO, NO_ALIAS);

}
