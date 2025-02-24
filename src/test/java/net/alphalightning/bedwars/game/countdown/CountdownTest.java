package net.alphalightning.bedwars.game.countdown;

import net.alphalightning.bedwars.BedWarsPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountdownTest {

    private ServerMock server;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    /**
     * Tests a regular countdown behaviour: Run 3 seconds and cancels regular
     */
    @Test
    public void testRegularCountdown() {
        BedWarsPlugin plugin = Mockito.mock(BedWarsPlugin.class);
        TestCountdown countdown = new TestCountdown(plugin, 3);
        TestListener listener = new TestListener();
        countdown.registerListener(listener);

        // Start countdown
        countdown.start();

        // Because runTaskTimer runs with a delay of 0L and a period of 20L, the first tick is performed on the next server tick.
        // Simulate the first tick: onTick(3) is called and seconds is decremented to 2.
        server.getScheduler().performOneTick();

        // For the next executions a period of 20 ticks has to be simulated.
        // Second tick (seconds = 2)
        for (int i = 0; i < 20; i++) {
            server.getScheduler().performOneTick();
        }

        // Third tick (seconds = 1)
        for (int i = 0; i < 20; i++) {
            server.getScheduler().performOneTick();
        }

        // Fourth tick (seconds = 0): onFinish() is called and the task will be canceled.
        for (int i = 0; i < 20; i++) {
            server.getScheduler().performOneTick();
        }

        List<String> expectedCountdownEvents = List.of("start", "tick: 3", "tick: 2", "tick: 1", "finish");
        List<String> expectedListenerEvents = List.of("listenerTick: 3", "listenerTick: 2", "listenerTick: 1", "listenerEnd");

        assertEquals(expectedCountdownEvents, countdown.events(), "Die Countdown-Ereignisse stimmen nicht mit der erwarteten Sequenz überein");
        assertEquals(expectedListenerEvents, listener.events(), "Die Listener-Ereignisse stimmen nicht mit der erwarteten Sequenz überein");
    }

    /**
     * Tests if after a manual cancellation no more ticks are proceeded
     */
    @Test
    public void testCountdownCancellation() {
        BedWarsPlugin plugin = Mockito.mock(BedWarsPlugin.class);
        TestCountdown countdown = new TestCountdown(plugin, 5);
        TestListener listener = new TestListener();
        countdown.registerListener(listener);

        // Start countdown
        countdown.start();

        // Simulate the first tick: onTick(5) is called and seconds is decremented to 4.
        server.getScheduler().performOneTick();

        // Cancel the countdown manually
        countdown.cancel();

        // Simulate more ticks. They should not trigger no more events because the task was canceled.
        for (int i = 0; i < 20; i++) {
            server.getScheduler().performOneTick();
        }

        List<String> expectedCountdownEvents = List.of("start", "tick: 5");
        List<String> expectedListenerEvents = List.of("listenerTick: 5");

        assertEquals(expectedCountdownEvents, countdown.events(), "Nach dem Abbruch sollten keine weiteren Countdown-Ereignisse auftreten");
        assertEquals(expectedListenerEvents, listener.events(), "Nach dem Abbruch sollten keine weiteren Listener-Ereignisse auftreten");
    }

}