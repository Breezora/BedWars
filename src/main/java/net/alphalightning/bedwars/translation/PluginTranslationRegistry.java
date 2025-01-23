package net.alphalightning.bedwars.translation;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.TranslationRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;

public final class PluginTranslationRegistry extends MiniMessageTranslator implements TranslationRegistry {

    private final TranslationRegistry delegate;

    public PluginTranslationRegistry(TranslationRegistry delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean contains(@NotNull String key) {
        return delegate.contains(key);
    }

    @Override
    public @NotNull Key name() {
        return delegate.name();
    }

    @Override
    protected @Nullable String getMiniMessageString(@NotNull String key, @NotNull Locale locale) {
        if (!delegate.contains(key)) {
            return null;
        }
        return Objects.requireNonNull(delegate.translate(key, locale)).toPattern();
    }

    @Override
    public void defaultLocale(@NotNull Locale locale) {
        delegate.defaultLocale(locale);
    }

    @Override
    public void register(@NotNull String key, @NotNull Locale locale, @NotNull MessageFormat format) {
        delegate.register(key, locale, format);
    }

    @Override
    public void unregister(@NotNull String key) {
        delegate.unregister(key);
    }

}