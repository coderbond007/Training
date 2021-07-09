package redis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SplittableRandom;

public class Main {
    public static final String PRIORITY_QUEUE = "PRIORITY_QUEUE";
    public static final String[] URLS = prepareURLS();
    public static final Jedis jedis = new Jedis();
    public static final List<String> queue = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        addURLs();
    }

    private static void addURLs() throws InterruptedException {
        SplittableRandom random = new SplittableRandom(System.currentTimeMillis());
        for (int iter = 0; iter < 20; ++iter) {
            String currentURL = URLS[random.nextInt(URLS.length)];
            jedis.zincrby(PRIORITY_QUEUE, 1, currentURL);
            System.out.println("Current URL : " + currentURL);
            queue.add(currentURL);
            printHighestPriority();
            Thread.sleep(500);
            removeURL();
        }
    }

    private static void removeURL() {
        SplittableRandom splittableRandom = new SplittableRandom(System.currentTimeMillis());
        int ind = splittableRandom.nextInt(queue.size());
        String removeURL = queue.get(ind);
        clearSET(removeURL);
        queue.remove(ind);
    }

    private static void printHighestPriority() {
        Set<String> nowURL = jedis.zrevrange(PRIORITY_QUEUE, 0, 0);
        System.out.println("NOW : " + nowURL);
    }

    private static String[] prepareURLS() {
        SplittableRandom random = new SplittableRandom(System.currentTimeMillis());
        String[] urls = new String[20];
        for (int i = 0; i < urls.length; ++i) {
            urls[i] = "www." + random.nextInt(1000) + ".com";
        }
        return urls;
    }

    private static void clearSET(final String removeURLName) {
        jedis.zrem(PRIORITY_QUEUE, removeURLName);
    }
}
