package net.alphalightning.bedwars.game.countdown;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Countdown {

    protected final BedWarsPlugin plugin;

    private final List<CountdownListener> listeners = new ArrayList<>();
    private BukkitTask task;
    private int seconds;

    public Countdown(BedWarsPlugin plugin, int seconds) {
        this.plugin = plugin;
        this.seconds = seconds;
    }

    public void registerListener(@NonNull CountdownListener listener) {
        this.listeners.add(listener);
    }

    public final void start() {
        onStart();
        this.task = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
            if (this.seconds > 0) {
                onTick(seconds); // Notify self
                this.listeners.forEach(listener -> listener.onTick(seconds)); // Notify listeners

                this.seconds--;

            } else {
                onFinish();
                this.listeners.forEach(CountdownListener::onEnd);
                cancel();
            }
        }, 0L, 20L);
    }

    public final void cancel() {
        if (this.task != null) {
            this.task.cancel();
        }
    }

    protected void onStart() {}

    protected abstract void onTick(int timeLeft);

    protected abstract void onFinish();
}
