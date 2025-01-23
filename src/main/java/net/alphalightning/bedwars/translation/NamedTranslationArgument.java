package net.alphalightning.bedwars.translation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.TranslationArgumentLike;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

@ApiStatus.NonExtendable
public interface NamedTranslationArgument {

    static @NotNull ComponentLike bool(final @TagPattern @NotNull String name, final boolean value) {
        return argument(name, TranslationArgument.bool(value));
    }

    static @NotNull ComponentLike numeric(final @TagPattern @NotNull String name, final @NotNull Number value) {
        return argument(name, TranslationArgument.numeric(value));
    }

    static @NotNull ComponentLike component(final @TagPattern @NotNull String name, final @NotNull ComponentLike value) {
        return argument(name, TranslationArgument.component(value));
    }

    static @NotNull ComponentLike argument(final @TagPattern @NotNull String name, final @NotNull TranslationArgumentLike argument) {
        return argument(name, requireNonNull(argument, "argument").asTranslationArgument());
    }

    static @NotNull ComponentLike argument(final @TagPattern @NotNull String name, final @NotNull TranslationArgument argument) {
        return Component.virtual(Void.class, new NamedTranslationArgumentImpl(name, argument));
    }

    @TagPattern
    @NotNull String name();

    @NotNull TranslationArgument translationArgument();

}
