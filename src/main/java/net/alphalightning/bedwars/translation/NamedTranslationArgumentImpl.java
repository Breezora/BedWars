package net.alphalightning.bedwars.translation;

import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.VirtualComponentRenderer;
import net.kyori.adventure.text.minimessage.internal.TagInternals;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Objects;

final class NamedTranslationArgumentImpl implements NamedTranslationArgument, VirtualComponentRenderer<Void> {

    private final @TagPattern @NotNull String name;
    private final @NotNull TranslationArgument translationArgument;

    NamedTranslationArgumentImpl(final @TagPattern @NotNull String name, final @NotNull TranslationArgument argument) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(argument, "argument");
        TagInternals.assertValidTagName(name);

        this.name = name;
        this.translationArgument = argument;
    }

    @Override
    public @TagPattern @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull TranslationArgument translationArgument() {
        return translationArgument;
    }

    @Override
    public @UnknownNullability ComponentLike apply(final @NotNull Void context) {
        return translationArgument;
    }

    @Override
    public @NotNull String fallbackString() {
        return ""; // Not for display purposes
    }
}
