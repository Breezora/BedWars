package net.alphalightning.bedwars.translation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import net.kyori.adventure.translation.Translator;
import net.kyori.examination.Examinable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface CustomGlobalTranslator extends Translator, Examinable {

    static @NotNull CustomGlobalTranslator translator() {
        return CustomGlobalTranslatorImpl.INSTANCE;
    }

    static @NotNull TranslatableComponentRenderer<Locale> renderer() {
        return CustomGlobalTranslatorImpl.INSTANCE.renderer;
    }

    static @NotNull Component render(final @NotNull Component component, final @NotNull Locale locale) {
        return renderer().render(component, locale);
    }

    @NotNull Iterable<? extends Translator> sources();

    boolean addSource(final @NotNull Translator source);

}
