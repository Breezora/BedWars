package net.alphalightning.bedwars.game.countdown;

import java.util.ArrayList;
import java.util.List;

public class TestListener implements CountdownListener {

    private final List<String> events = new ArrayList<>();

    @Override
    public void onTick(int timeLeft) {
        events.add("listenerTick: " + timeLeft);
    }

    @Override
    public void onEnd() {
        events.add("listenerEnd");
    }

    public List<String> events() {
        return events;
    }
}
