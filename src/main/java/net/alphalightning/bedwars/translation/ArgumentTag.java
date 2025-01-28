package net.alphalightning.bedwars.translation;

import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.VirtualComponent;
import net.kyori.adventure.text.VirtualComponentRenderer;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

final class ArgumentTag implements TagResolver {

    private static final String NAME = "argument";
    private static final String ALIAS = "arg";

    private final List<? extends ComponentLike> argumentComponents;
    private final Map<String, ComponentLike> namedArguments;

    ArgumentTag(final @NotNull List<? extends ComponentLike> argumentComponents) {
        this.argumentComponents = new ArrayList<>(Objects.requireNonNull(argumentComponents, "argumentComponents"));

        final Map<String, ComponentLike> namedArgumentMap = new HashMap<>(this.argumentComponents.size());

        for (final ComponentLike argument : this.argumentComponents) {
            if (argument instanceof TranslationArgument translationArgument) {
                if (translationArgument.value() instanceof VirtualComponent virtual) {
                    final VirtualComponentRenderer<?> renderer = virtual.renderer();

                    if (renderer instanceof NamedTranslationArgument namedArgument) {
                        namedArgumentMap.put(namedArgument.name(), namedArgument.translationArgument());

                    }
                }
            }
        }

        this.namedArguments = Collections.unmodifiableMap(namedArgumentMap);
    }

    @Override
    public @Nullable Tag resolve(final @NotNull String name, final @NotNull ArgumentQueue arguments, final @NotNull Context ctx) throws ParsingException {
        if (name.equals(NAME) || name.equals(ALIAS)) {
            final int index = arguments.popOr("No argument number provided")
                    .asInt().orElseThrow(() -> ctx.newException("Invalid argument number", arguments));

            if (index < 0 || index >= this.argumentComponents.size()) {
                throw ctx.newException("Invalid argument number", arguments);
            }

            return Tag.inserting(this.argumentComponents.get(index));
        } else {
            final ComponentLike namedArgument = this.namedArguments.get(name);

            if (namedArgument != null) {
                return Tag.inserting(namedArgument);
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean has(final @NotNull String name) {
        return name.equals(NAME) || name.equals(ALIAS) || this.namedArguments.containsKey(name);
    }
}
