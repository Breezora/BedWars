package net.alphalightning.bedwars.translation;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.TranslationRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

public class PluginTranslationRegistry extends PluginMiniMassageTranslator implements TranslationRegistry {

    private final TranslationRegistry delegate;

    public PluginTranslationRegistry(TranslationRegistry delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    public boolean contains(@NotNull String key) {
        return this.delegate.contains(key);
    }

    @Override
    public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
        return null;
    }

    @Override
    public void defaultLocale(@NotNull Locale locale) {
        this.delegate.defaultLocale(locale);
    }

    @Override
    public void register(@NotNull String key, @NotNull Locale locale, @NotNull MessageFormat format) {
        this.delegate.register(key, locale, format);
    }

    @Override
    public void unregister(@NotNull String key) {
        this.delegate.unregister(key);
    }

    @Override
    public @NotNull Key name() {
        return this.delegate.name();
    }
}
