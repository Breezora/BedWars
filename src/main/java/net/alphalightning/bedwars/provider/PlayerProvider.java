package net.alphalightning.bedwars.provider;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface PlayerProvider {

    @NotNull Collection<Player> players();

    int count();

}
