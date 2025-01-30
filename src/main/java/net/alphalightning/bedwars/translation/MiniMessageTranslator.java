package net.alphalightning.bedwars.translation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.Translator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * A {@link Translator} implementation that translates strings using MiniMessage.
 *
 * <p>To use this feature, you should extend this class, implementing the
 * {@link #getMiniMessageString(String, Locale)} method to return the MiniMessage string
 * for a given key and locale.
 * After that, you can use the translator as-is using
 * {@link #translate(TranslatableComponent, Locale)}, or automatically (depending on the
 * implementing platform) using the {@link GlobalTranslator}.</p>
 *
 * <p>This system supports arguments using {@code <arg:0>} tags (or {@code argument},
 * where {@code 0} is the index of the argument to use).
 * Alternatively, you can use named arguments by creating the translatable component
 * with {@link NamedTranslationArgument} as the arguments.
 * The provided {@link NamedTranslationArgument#name() name} will be available for use in
 * a tag as {@code <name>}, in addition to the index-based {@code arg} tag.</p>
 *
 * @see Translator
 * @see GlobalTranslator
 * @see NamedTranslationArgument
 * @since 4.19.0
 */
public abstract class MiniMessageTranslator implements Translator {

    private final MiniMessage miniMessage;

    /**
     * Constructor for a MiniMessageTranslator using the default MiniMessage instance.
     *
     * @see MiniMessage#miniMessage()
     * @since 4.19.0
     */
    public MiniMessageTranslator() {
        this(MiniMessage.miniMessage());
    }

    /**
     * Constructor for a MiniMessageTranslator using a specific MiniMessage instance.
     *
     * @param miniMessage the MiniMessage instance
     * @see MiniMessage#miniMessage()
     * @since 4.19.0
     */
    public MiniMessageTranslator(MiniMessage miniMessage) {
        this.miniMessage = requireNonNull(miniMessage, "miniMessage");
    }

    /**
     * Returns a raw MiniMessage string for the given key.
     *
     * <p>If no string is found for the given key, returning {@code null} will use the
     * {@link TranslatableComponent#fallback() translatable component's fallback} (or the
     * key itself).</p>
     *
     * @param key    the key
     * @param locale the locale
     * @return the resulting MiniMessage string
     * @since 4.19.0
     */
    protected abstract @Nullable String getMiniMessageString(final @NotNull String key, final @NotNull Locale locale);

    @Override
    public @Nullable MessageFormat translate(final @NotNull String key, final @NotNull Locale locale) {
        return null;
    }

    @Override
    public @Nullable Component translate(@NotNull TranslatableComponent component, @NotNull Locale locale) {
        return this.translate(component, locale, 0);
    }

    private @Nullable Component translate(final @NotNull TranslatableComponent component, final @NotNull Locale locale, final int depth) {
        if (depth >= 128) {
            return null;
        }

        final String miniMessageString = this.getMiniMessageString(component.key(), locale);
        if (miniMessageString == null) {
            return null;
        }

        final List<ComponentLike> translatedArguments = new ArrayList<>();
        for (final Object argument : component.arguments()) {
            if (argument instanceof TranslatableComponent translatableArgument) {
                Component translatedArgument = this.translate(translatableArgument, locale, depth + 1);
                translatedArguments.add(translatedArgument != null ? translatedArgument : translatableArgument);

            } else if (argument instanceof ComponentLike componentLike) {
                translatedArguments.add(componentLike);
            }
        }

        Component resultingComponent = this.miniMessage.deserialize(miniMessageString, new ArgumentTag(translatedArguments));

        final List<Component> newChildren = new ArrayList<>();
        for (final Component child : component.children()) {
            if (child instanceof TranslatableComponent translatableChild) {
                Component translatedChild = this.translate(translatableChild, locale, depth + 1);
                newChildren.add(translatedChild != null ? translatedChild : child);

            } else {
                newChildren.add(child);
            }
        }

        return resultingComponent.children(newChildren);
    }

    /*
    @Override
    public @Nullable Component translate(final @NotNull TranslatableComponent component, final @NotNull Locale locale) {
        final String miniMessageString = this.getMiniMessageString(component.key(), locale);
        if (miniMessageString == null) {
            return null;
        }

        final Component resultingComponent;

        if (component.arguments().isEmpty()) {
            resultingComponent = this.miniMessage.deserialize(miniMessageString);
        } else {
            resultingComponent = this.miniMessage.deserialize(miniMessageString, new ArgumentTag(component.arguments()));
        }

        if (component.children().isEmpty()) {
            return resultingComponent;
        } else {
            return resultingComponent.children(component.children());
        }
    }

    private @Nullable Component translate(final @NotNull TranslatableComponent component, final @NotNull Locale locale, final int depth) {
        if (depth >= 128) {
            return null;
        }

        final String miniMessageString = this.getMiniMessageString(component.key(), locale);
        if (miniMessageString == null) {
            return null;
        }

        Component translation = this.translate(component, locale);
        if (translation == null) {
            return null;
        }

        final List<Component> children = translation.children();
        if (translation instanceof TranslatableComponent translatable) {
            translation = this.translate(translatable, locale, depth + 1);
        }

        final List<Component> newChildren = new ArrayList<>();
        for (final Component child : children) {
            if (child instanceof TranslatableComponent translatableChild) {
                final Component translatedChild = this.translate(translatableChild, locale, depth + 1);
                newChildren.add(translatedChild != null ? translatedChild : child);

            } else {
                newChildren.add(child);
            }
            return translation != null ? translation.children(newChildren) : null;
        }

        return null;
    }
     */

}
