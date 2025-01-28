package net.alphalightning.bedwars.translation;

import net.kyori.adventure.internal.Internals;
import net.kyori.adventure.internal.properties.AdventureProperties;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.translation.Translator;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class PluginTranslationRegistry implements TranslationRegistry, Examinable {

    private final Key name;
    private final Map<String, PluginTranslationRegistry.Translation> translations = new ConcurrentHashMap<>();
    private Locale defaultLocale = Locale.GERMAN;

    public PluginTranslationRegistry(Key name) {
        this.name = name;
    }

    @Override
    public boolean contains(@NotNull String key) {
        return this.translations.containsKey(key);
    }

    @Override
    public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
        Translation translation = this.translations.get(key);
        if (translation == null) {
            return null;
        }
        return translation.translate(locale);
    }

    @Override
    public void defaultLocale(@NotNull Locale locale) {
        this.defaultLocale = requireNonNull(locale, "defaultLocale");
    }

    @Override
    public void register(@NotNull String key, @NotNull Locale locale, @NotNull MessageFormat format) {
        this.translations.computeIfAbsent(key, Translation::new).register(locale, format);
    }

    @Override
    public void unregister(final @NotNull String key) {
        this.translations.remove(key);
    }

    @Override
    public @NotNull Key name() {
        return this.name;
    }

    @Override
    public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("translations", this.translations));
    }

    // Start Translation

    final class Translation implements Examinable {
        private final String key;
        private final Map<Locale, MessageFormat> formats;

        Translation(final @NotNull String key) {
            this.key = requireNonNull(key, "translation key");
            this.formats = new ConcurrentHashMap<>();
        }

        void register(final @NotNull Locale locale, final @NotNull MessageFormat format) {
            if (this.formats.putIfAbsent(requireNonNull(locale, "locale"), requireNonNull(format, "message format")) != null) {
                throw new IllegalArgumentException(String.format("Translation already exists: %s for %s", this.key, locale));
            }
        }

        @Nullable MessageFormat translate(final @NotNull Locale locale) {
            MessageFormat format = this.formats.get(requireNonNull(locale, "locale"));
            if (format == null) {
                format = this.formats.get(Locale.of(locale.getLanguage())); // try without country
                if (format == null) {
                    format = this.formats.get(PluginTranslationRegistry.this.defaultLocale); // try local default locale
                    if (format == null) {
                        format = this.formats.get(TranslationLocales.global()); // try global default locale
                    }
                }
            }
            return format;
        }

        @Override
        public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(
                    ExaminableProperty.of("key", this.key),
                    ExaminableProperty.of("formats", this.formats)
            );
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) return true;
            if (!(other instanceof Translation that)) return false;
            return this.key.equals(that.key) && this.formats.equals(that.formats);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.key, this.formats);
        }

        @Override
        public String toString() {
            return Internals.toString(this);
        }
    }

    // Starts locales

    static final class TranslationLocales {
        private static final Supplier<Locale> GLOBAL;

        static {
            final @Nullable String property = AdventureProperties.DEFAULT_TRANSLATION_LOCALE.value();
            if (property == null || property.isEmpty()) {
                GLOBAL = () -> Locale.US;
            } else if (property.equals("system")) {
                GLOBAL = Locale::getDefault;
            } else {
                final Locale locale = Translator.parseLocale(property);
                GLOBAL = () -> locale;
            }
        }

        private TranslationLocales() {
        }

        static Locale global() {
            return GLOBAL.get();
        }
    }
}
