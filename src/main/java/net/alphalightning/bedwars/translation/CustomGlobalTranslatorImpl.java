package net.alphalightning.bedwars.translation;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import net.kyori.adventure.translation.Translator;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

final class CustomGlobalTranslatorImpl implements CustomGlobalTranslator {

    static final CustomGlobalTranslatorImpl INSTANCE = new CustomGlobalTranslatorImpl();
    private static final Key NAME = Key.key("bedwars:global");

    final TranslatableComponentRenderer<Locale> renderer = TranslatableComponentRenderer.usingTranslationSource(this);
    private final Set<Translator> sources = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private CustomGlobalTranslatorImpl() {
    }

    @Override
    public @NotNull Key name() {
        return NAME;
    }

    @Override
    public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
        requireNonNull(key, "key");
        requireNonNull(locale, "locale");

        for (final Translator source : sources) {
            final MessageFormat translation = source.translate(key, locale);
            if (translation != null) {
                return translation;
            }
        }
        return null;
    }

    @Override
    public @NotNull Set<Translator> sources() {
        return Collections.unmodifiableSet(sources);
    }

    @Override
    public boolean addSource(@NotNull Translator source) {
        requireNonNull(source, "source");

        if (source == this) {
            throw new IllegalArgumentException("CustomGlobalTranslationSource");
        }
        return sources.add(source);
    }

    @Override
    public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("sources", sources));
    }

    @Override
    public @Nullable Component translate(@NotNull TranslatableComponent component, @NotNull Locale locale) {
        requireNonNull(component, "component");
        requireNonNull(locale, "locale");

        return this.translate(component, locale, 0);
    }

    private @Nullable Component translate(final @NotNull TranslatableComponent component, final @NotNull Locale locale, final int depth) {
        if (depth >= 128) {
            return null;
        }

        for (final Translator source : sources) {
            Component translation = source.translate(component, locale);
            if (translation == null) {
                continue;
            }

            final List<Component> children = translation.children();
            if (translation instanceof TranslatableComponent) {
                translation = this.translate((TranslatableComponent) translation, locale, depth + 1);
            }

            final List<Component> newChildren = new ArrayList<>();
            for (final Component child : children) {
                if (child instanceof TranslatableComponent) {
                    final Component childTranslation = this.translate((TranslatableComponent) child, locale, depth + 1);
                    newChildren.add(childTranslation != null ? childTranslation : child);
                } else {
                    newChildren.add(child);
                }
            }

            if (translation == null) {
                return null;
            }
            return translation.children(newChildren);
        }
        return null;
    }
}
