package net.alphalightning.bedwars.game.countdown;

import net.alphalightning.bedwars.BedWarsPlugin;

import java.util.ArrayList;
import java.util.List;

public class TestCountdown extends Countdown {

    private final List<String> events = new ArrayList<>();

    public TestCountdown(BedWarsPlugin plugin, int seconds) {
        super(plugin, seconds);
    }

    @Override
    protected void onStart() {
        events.add("start");
    }

    @Override
    protected void onTick(int timeLeft) {
        events.add("tick: " + timeLeft);
    }

    @Override
    protected void onFinish() {
        events.add("finish");
    }

    public List<String> events() {
        return events;
    }
}
