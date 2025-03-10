package net.alphalightning.bedwars.game.countdown;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Countdown {

    private enum State {IDLE, RUNNING}

    protected final BedWarsPlugin plugin;
    private final List<CountdownListener> listeners = new ArrayList<>();
    private final int duration;
    private BukkitTask task;
    private int remainingTime;

    private State state = State.IDLE;
    private int idleTickCounter = 0;

    public Countdown(BedWarsPlugin plugin, int duration) {
        this.plugin = plugin;
        this.duration = duration;
        this.remainingTime = duration;
    }

    public void registerListener(@NonNull CountdownListener listener) {
        listeners.add(listener);
    }

    public final void start() {
        if (task != null) return; // Prevent multiple start calls

        task = Bukkit.getScheduler().runTaskTimer(plugin, this::runCountdown, 0L, 20L);
    }

    public final void cancel() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    private void runCountdown() {
        if (state == State.IDLE) {
            handleIdleState();
        } else if (state == State.RUNNING) {
            handleRunningState();
        }
    }

    private void handleIdleState() {
        if (isStartingConditionMet()) {
            transitionToRunningState();
        } else {
            idleTickCounter++;
            if (idleTickCounter % 30 == 0) {
                onIdleTick();
            }
        }
    }

    private void handleRunningState() {
        if (!isStartingConditionMet()) {
            onAbort();
            notifyAbort();
            resetCountdown();
            return;
        }

        if (remainingTime > 0) {
            notifyTick(remainingTime);
            onTick(remainingTime);
            remainingTime--;
        } else {
            onFinish();
            notifyEnd();
            resetCountdown();
        }
    }

    private void transitionToRunningState() {
        state = State.RUNNING;
        idleTickCounter = 0;
        onStart();
        tick();
    }

    private void resetCountdown() {
        state = State.IDLE;
        remainingTime = duration;
        idleTickCounter = 0;
    }

    private void tick() {
        notifyTick(remainingTime);
        onTick(remainingTime);
        remainingTime--;
    }

    private void notifyTick(int timeLeft) {
        listeners.forEach(listener -> listener.onTick(timeLeft));
    }

    private void notifyEnd() {
        listeners.forEach(CountdownListener::onEnd);
    }

    private void notifyAbort() { listeners.forEach(CountdownListener::onAbort); }

    public int duration() {
        return duration;
    }

    public int remainingTime() { return remainingTime; }

    public boolean isRunning() {
        return this.state == State.RUNNING;
    }

    // --------------------- Template Method Hooks ---------------------

    protected void onStart() {
    }

    protected abstract void onTick(int timeLeft);

    protected abstract void onFinish();

    protected abstract void onAbort();

    // --------------------- Idle Hooks ---------------------

    protected abstract void onIdleTick();

    protected abstract boolean isStartingConditionMet();
}
