package net.alphalightning.bedwars.commands;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommand;
import net.alphalightning.bedwars.commands.cloud.sender.PaperCommandSource;
import net.alphalightning.bedwars.commands.cloud.sender.PaperPlayerCommandSource;
import net.alphalightning.bedwars.config.Configuration;
import net.alphalightning.bedwars.game.countdown.LobbyCountdown;
import net.alphalightning.bedwars.game.state.states.LobbyState;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.permission.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StartCommand extends PaperCommand<@NotNull BedWarsPlugin> {

    private static final List<Permission> PERMISSIONS = List.of(
            Permission.of("bedwars.command.start"),
            Permission.of("bedwars.admin"),
            Permission.of("bedwars.*")
    );
    private final Configuration configuration;

    public StartCommand(@NotNull BedWarsPlugin plugin) {
        super(plugin);
        this.configuration = plugin.configuration();
    }

    @Override
    public void register(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("start")
                .commandDescription(RichDescription.translatable("command.start.description"))
                .senderType(PaperPlayerCommandSource.class)
                .permission(Permission.anyOf(PERMISSIONS))
                .handler(this::runCommand)
        );
    }

    private void runCommand(@NotNull CommandContext<PaperPlayerCommandSource> context) {
        Player player = (Player) context.sender().plattformSender();

        if (!(super.plugin.gameStateContext().currentState() instanceof LobbyState lobbyState)) {
            player.sendMessage(Component.translatable("command.error.gamestate"));
            return;
        }

        LobbyCountdown countdown = lobbyState.countdown();

        if (!canRoundStart()) {
            player.sendMessage(Component.translatable("command.start.error.players"));
            return;
        }
        if (isStarting(countdown)) {
            player.sendMessage(Component.translatable("command.start.error.starting"));
            return;
        }

        forceStart(player, countdown);
    }

    private boolean isStarting(@NotNull LobbyCountdown countdown) {
        return countdown.isRunning() && (countdown.remainingTime() <= this.configuration.main().forceStartTime());
    }

    private boolean canRoundStart() {
        int requiredPlayers = calculateRequiredPlayers();

        if (requiredPlayers == -1) {
            super.plugin.getComponentLogger().error(Component.translatable("command.start.error.factor"));
            return false;
        }
        return Bukkit.getOnlinePlayers().size() >= requiredPlayers;
    }

    private int calculateRequiredPlayers() {
        double factor = this.configuration.main().forceStartFactor();
        if (factor <= 0 || factor > 1) {
            return -1;
        }

        double calculated = Bukkit.getMaxPlayers() * factor;

        return (int) Math.floor(calculated);
    }

    private void forceStart(@NotNull Player player, @NotNull LobbyCountdown countdown) {
        int time = this.configuration.main().forceStartTime();

        if (time < 0) {
            super.plugin.getComponentLogger().error(Component.translatable("command.start.error.time"));
            return;
        }

        countdown.remainingTime(time);
        player.sendMessage(Component.translatable("command.start.success"));
    }
}
