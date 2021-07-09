package threads;

import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        getMaxThread();
    }

    public static void getMaxThread() throws Exception {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

        // Max Callable
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        MaxCallable maxCallable = new MaxCallable(a);
        Future<Integer> iAmFuture = fixedThreadPool.submit(maxCallable);
        Integer max = iAmFuture.get();
        System.out.println(max);

        scheduledThreadPool.scheduleAtFixedRate(new CronRunnable(),0,0, TimeUnit.SECONDS);
    }

    public static void mutliThreadPrint() throws InterruptedException {
        SplittableRandom splittableRandom = new SplittableRandom();
        MyThread1[] myThread1s = new MyThread1[50];
        for (int it = 0; it < myThread1s.length; ++it) {
            myThread1s[it] = new MyThread1(splittableRandom.nextInt(100000) + "");
            myThread1s[it].start();
        }
        for (int it = 0; it < myThread1s.length; ++it) {
            myThread1s[it].join();
        }
        System.out.println("{{" + MyThread1.counter.getCount() + "}}");
    }
}