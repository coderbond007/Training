package net.media.training.live.isp;

import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 11, 2011
 * Time: 10:29:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class Timer {
    public void register(long timeOut, final TimerClient timerClient) {
        java.util.Timer timerUtility = new java.util.Timer();
        timerUtility.schedule(new TimerTask() {
            @Override
            public void run() {
                timerClient.timeOutCallback();
            }
        }, timeOut);
    }
}
