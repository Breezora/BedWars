package net.alphalightning.bedwars.translation;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.TranslationRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

public class PluginMiniMassageTranslator extends MiniMessageTranslator {

    private final TranslationRegistry translationRegistry;

    public PluginMiniMassageTranslator(TranslationRegistry translationRegistry) {
        this.translationRegistry = translationRegistry;
    }

    @Override
    protected @Nullable String getMiniMessageString(@NotNull String key, @NotNull Locale locale) {
        if (!translationRegistry.contains(key)) {
            return null;
        }
        MessageFormat messageFormat = translationRegistry.translate(key, locale);

        if (messageFormat == null) {
            return null;
        }
        return messageFormat.toPattern();
    }

    @Override
    public @NotNull Key name() {
        return Key.key("bedwars:translator");
    }
}
