package net.alphalightning.bedwars.manager;

import net.alphalightning.bedwars.exception.AlreadyRegisteredException;
import net.alphalightning.bedwars.exception.NotRegisteredException;
import net.alphalightning.bedwars.provider.TextProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TextManager {

    @NotNull List<String> registeredTexts();

    @NotNull TextProvider texts();

    void registerText(String text) throws AlreadyRegisteredException;

    void unregisterText(String text) throws NotRegisteredException;

}
