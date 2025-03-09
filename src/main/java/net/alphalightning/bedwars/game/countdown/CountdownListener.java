package net.alphalightning.bedwars.game.countdown;

public interface CountdownListener {

    void onTick(int timeLeft);

    default void onEnd() {}

}
