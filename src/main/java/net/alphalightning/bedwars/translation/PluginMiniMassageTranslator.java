package net.alphalightning.bedwars.translation;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.TranslationRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

public class PluginMiniMassageTranslator extends MiniMessageTranslator implements TranslationRegistry {

    private final TranslationRegistry translationRegistry;

    public PluginMiniMassageTranslator(TranslationRegistry translationRegistry) {
        super(MiniMessage.miniMessage());
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
    public boolean contains(@NotNull String key) {
        return translationRegistry.contains(key);
    }

    @Override
    public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
        return null;
    }

    @Override
    public @NotNull Key name() {
        return translationRegistry.name();
    }

    @Override
    public void defaultLocale(@NotNull Locale locale) {
        translationRegistry.defaultLocale(locale);
    }

    @Override
    public void register(@NotNull String key, @NotNull Locale locale, @NotNull MessageFormat format) {
        translationRegistry.register(key, locale, format);
    }

    @Override
    public void unregister(@NotNull String key) {
        translationRegistry.unregister(key);
    }
}
