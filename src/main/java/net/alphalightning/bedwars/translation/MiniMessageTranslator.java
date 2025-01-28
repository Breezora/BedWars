package net.alphalightning.bedwars.translation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.Translator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
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
     * @param key the key
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
}
