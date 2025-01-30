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

        final List<Component> children = new ArrayList<>();
        for (final Component child : translation.children()) {
            children.add(this.translateRecursively(child, locale, depth + 1));
        }

        Component result = translation.children(children);

        if (component.key().equalsIgnoreCase("mapsetup.stage.9.name")) {
            Bukkit.getLogger().info("Result: " + result);
        }

        return result;
    }

    private Component translateRecursively(Component component, Locale locale, int depth) {
        if (component instanceof TranslatableComponent translatable) { // Translate recursively if component is translatable
            Component translated = this.translate(translatable, locale, depth);

            if (translated != null) { // Adopt children from original component
                List<Component> mergedChildren = new ArrayList<>(translated.children());
                mergedChildren.addAll(translatable.children());

                return translated.children(mergedChildren);
            }
            return translatable;
        }

        // Translate every child
        List<Component> children = new ArrayList<>();
        for (Component child : component.children()) {
            children.add(this.translateRecursively(child, locale, depth + 1));
        }

        return component.children(children); // Component contains translated children
    }
}
