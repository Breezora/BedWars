package net.alphalightning.bedwars.translation;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

public class PluginMiniMassageTranslator extends MiniMessageTranslator {

    private final MiniMessageTranslationStore store;

    public PluginMiniMassageTranslator(MiniMessage miniMessage, MiniMessageTranslationStore store) {
        super(miniMessage);
        this.store = store;
    }

    @Override
    protected @Nullable String getMiniMessageString(@NotNull String key, @NotNull Locale locale) {
        if (!store.contains(key)) {
            return null;
        }
        MessageFormat messageFormat = store.translate(key, locale);

        if (messageFormat == null) {
            return null;
        }
        return messageFormat.toPattern();
    }

    @Override
    public @NotNull Key name() {
        return Key.key("bedwars:translator");
    }
}
