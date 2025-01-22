package net.alphalightning.bedwars.translation;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.translation.TranslationRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class PluginTranslationRegistry implements TranslationRegistry {

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
    public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
        return null;
    }

    @Override
    public @Nullable Component translate(@NotNull TranslatableComponent component, @NotNull Locale locale) {
        final MessageFormat translationFormat = delegate.translate(component.key(), locale);

        if (translationFormat == null) return null;

        final MiniMessage miniMessage = MiniMessage.miniMessage();
        final String miniString = translationFormat.toPattern();
        final Component resulting;

        if (component.arguments().isEmpty()) {
            resulting = miniMessage.deserialize(miniString);
        } else {
            resulting = miniMessage.deserialize(miniString, new ArgumentTag(component.arguments()));
        }

        if (component.children().isEmpty()) {
            return resulting;
        } else {
            return resulting.children(component.children());
        }
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

    private record ArgumentTag(List<? extends ComponentLike> argumentComponents) implements TagResolver {
        private static final String NAME = "argument";
        private static final String ALIAS = "arg";

        private ArgumentTag(final @NotNull List<? extends ComponentLike> argumentComponents) {
            this.argumentComponents = Objects.requireNonNull(argumentComponents, "argumentComponents");
        }

        @Override
        public Tag resolve(@NotNull String name, @NotNull ArgumentQueue arguments, @NotNull Context ctx) throws ParsingException {
            final int index = arguments.popOr("No argument number provided")
                    .asInt().orElseThrow(() -> ctx.newException("Invalid argument number", arguments));

            if (index < 0 || index >= argumentComponents.size()) throw ctx.newException("Invalid argument number", arguments);

            return Tag.inserting(argumentComponents.get(index));
        }

        @Override
        public boolean has(@NotNull String name) {
            return name.equals(NAME) || name.equals(ALIAS);
        }
    }
}