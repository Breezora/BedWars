package net.alphalightning.bedwars.translation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.Translator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

public abstract class MiniMessageTranslator implements Translator {

    private final MiniMessage miniMessage;

    public MiniMessageTranslator() {
        this(MiniMessage.miniMessage());
    }

    public MiniMessageTranslator(MiniMessage miniMessage) {
        this.miniMessage = requireNonNull(miniMessage, "miniMessage");
    }

    protected abstract @Nullable String getMiniMessageString(final @NotNull String key, final @NotNull Locale locale);

    @Override
    public @Nullable MessageFormat translate(final @NotNull String key, final @NotNull Locale locale) {
        return null;
    }

    @Override
    public @Nullable Component translate(final @NotNull TranslatableComponent component, final @NotNull Locale locale) {
        final String miniMessageString = this.getMiniMessageString(component.key(), locale);
        System.out.println(miniMessageString);

        if (miniMessageString == null) {
            return null;
        }

        final Component resultingComponent;

        if (component.arguments().isEmpty()) {
            System.out.println("Keine args");
            resultingComponent = this.miniMessage.deserialize(miniMessageString);
        } else {
            System.out.println("Args gefunden");
            resultingComponent = this.miniMessage.deserialize(miniMessageString, new ArgumentTag(component.arguments()));
        }

        if (component.children().isEmpty()) {
            return resultingComponent;
        } else {
            return resultingComponent.children(component.children());
        }
    }
}
