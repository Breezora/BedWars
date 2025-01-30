package net.alphalightning.bedwars.translation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.Translator;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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
    public @Nullable Component translate(@NotNull TranslatableComponent component, @NotNull Locale locale) {
        requireNonNull(component, "component");
        requireNonNull(locale, "locale");
        return this.translate(component, locale, 0);
    }

    private @Nullable Component translate(final @NotNull TranslatableComponent component, @NotNull Locale locale, final int depth) {
        final String miniMessageString = this.getMiniMessageString(component.key(), locale);
        if (miniMessageString == null) {
            return null;
        }

        Component translation;
        if (component.arguments().isEmpty()) {
            translation = this.miniMessage.deserialize(miniMessageString);
        } else {
            translation = this.miniMessage.deserialize(miniMessageString, new ArgumentTag(component.arguments()));
        }

        final List<Component> children = translation.children();
        if (translation instanceof TranslatableComponent translatable) {
            Bukkit.getLogger().info("Translation is translatable");
            translation = this.translate(translatable, locale, depth + 1);
        }

        final List<Component> newChildren = new ArrayList<>();
        for (Component child : children) {
            if (child instanceof TranslatableComponent translatable) {
                final Component childTranslation = this.translate(translatable, locale, depth + 1);
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
}
