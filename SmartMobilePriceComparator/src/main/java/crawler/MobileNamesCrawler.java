package crawler;

import clients.PrintStreamClient;
import net.media.mnetcrawler.CrawlerConfig;
import net.media.mnetcrawler.DefaultProxyCrawlerConfig;
import net.media.mnetcrawler.SyncCrawler;
import net.media.mnetcrawler.bean.SyncCrawlResponse;
import net.media.mnetcrawler.bean.UserAgentType;
import net.media.mnetcrawler.util.RandomUserAgentManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static constants.Constants.CRAWLER_APP_NAME;
import static constants.Constants.MOBILE_NAMES_PATH;

public class MobileNamesCrawler {
    public static void main(String[] args) throws FileNotFoundException {
        final long startTime = System.currentTimeMillis();
        Queue<String> queue = new ArrayDeque<>();
        queue.add("https://www.mysmartprice.com/mobile/pricelist/mobile-price-list-in-india.html");
        final String LINK_PAGE = "https://www.mysmartprice.com/mobile/pricelist/pages/mobile-price-list-in-india-$value.html";
        for (int it = 2; it <= 61; ++it) {
            queue.add(LINK_PAGE.replace("$value", "" + it));
        }
//        System.out.println("Added all pages link to the queue");
        List<String> mobileNames = null;
        try {
            mobileNames = crawl(queue);
//            System.out.println("Writing data to file");
            PrintWriter out = new PrintWriter(new FileOutputStream(MOBILE_NAMES_PATH));
            for (String mobile : mobileNames) {
                out.println(mobile);
            }
            out.flush();
            out.close();
//            System.out.println("Data written successfully");
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        final long endTime = System.currentTimeMillis();
//        System.out.println("Time taken for getting all names : " + (endTime - startTime) + "ms");
    }

    public static final List<String> crawl(Queue<String> queue) throws Exception {
        final long startTime = System.currentTimeMillis();
//        System.out.println("Inside crawl method");

        CrawlerConfig crawlerConfig = new DefaultProxyCrawlerConfig(CRAWLER_APP_NAME, new RandomUserAgentManager(UserAgentType.DESKTOP));
        SyncCrawler syncCrawler = new SyncCrawler(crawlerConfig);
        List<String> modelNames = new ArrayList<>();

        int times = 0;
        while (!queue.isEmpty()) {
            String currentPageLink = queue.poll();
//            System.out.println("Current Link : " + currentPageLink);
            ++times;
            if (times > 62) throw new RuntimeException();
            try {
                SyncCrawlResponse response = syncCrawler.crawl(currentPageLink);
                String content = response.getContent();
                Document document = Jsoup.parse(content);
                Elements elements = document.getElementsByClass("prdct-item__name");
                for (Element element : elements) {
                    modelNames.add(element.text());
                }
            } catch (Exception e) {
                try {
                    e.printStackTrace(PrintStreamClient.getClient());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        final long endTime = System.currentTimeMillis();
//        System.out.println("Time taken for crawl method : " + (endTime - startTime) + "ms");
        return modelNames;
    }
}
