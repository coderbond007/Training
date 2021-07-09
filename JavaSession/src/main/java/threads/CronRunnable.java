package threads;

import java.util.Date;

public class CronRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("I am cron and started execution on : " + new Date());
    }
}
