package net.media.training.live.isp;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 11, 2011
 * Time: 10:26:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimedDoor extends Door implements TimerClient {
    private static final int TIME_OUT = 100;

    public TimedDoor(Timer timer) {
        timer.register(TIME_OUT, this);
    }

    public void timeOutCallback() {
        isLocked = true;
    }
}
