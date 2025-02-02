package net.alphalightning.bedwars.manager.impl;

import net.alphalightning.bedwars.exception.AlreadyRegisteredException;
import net.alphalightning.bedwars.exception.NotRegisteredException;
import net.alphalightning.bedwars.manager.TextManager;
import net.alphalightning.bedwars.provider.TextProvider;
import net.alphalightning.bedwars.provider.impl.MapNameProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MapNameManager implements TextManager {

    private final Queue<String> mapNames = new ConcurrentLinkedQueue<>();
    private final TextProvider allTextProvider = new MapNameProvider(this.mapNames::stream);

    @Override
    public @NotNull List<String> registeredTexts() {
        return this.mapNames.stream().toList();
    }

    @Override
    public @NotNull TextProvider texts() {
        return this.allTextProvider;
    }

    @Override
    public void registerText(@NotNull String text) throws AlreadyRegisteredException {
        if (this.allTextProvider.has(text.toLowerCase())) {
            throw new AlreadyRegisteredException();
        }
        mapNames.add(text.toLowerCase());
    }

    @Override
    public void unregisterText(@NotNull String text) throws NotRegisteredException {
        if (!this.allTextProvider.has(text.toLowerCase())) {
            throw new NotRegisteredException();
        }
        mapNames.remove(text);
    }
}
