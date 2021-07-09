package crawler;

import Bean.SiteDataParsed;
import net.media.mnetcrawler.CrawlerConfig;
import net.media.mnetcrawler.DefaultProxyCrawlerConfig;
import net.media.mnetcrawler.SyncCrawler;
import net.media.mnetcrawler.bean.SyncCrawlResponse;
import net.media.mnetcrawler.bean.UserAgentType;
import net.media.mnetcrawler.util.RandomUserAgentManager;
import parsers.FlipkartParser;
import parsers.Parser;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class SampleCrawler {
    public static final String PATH = System.getProperty("user.dir") + "/data/";
    public static final String LINK_TO_WEBSITE = "https://www.flipkart.com/realme-3-dynamic-black-64-gb/p/itmfe68wrbfnzqwz?pid=MOBFFVFDJCCUZDGT&srno=b_1_1&otracker=clp_omu_Mobile%2BNew%2BLaunches_1_5.dealCard.OMU_mobile-phones-store_mobile-phones-store_X95WZDUDIR3U_5&otracker1=clp_omu_PINNED_neo%2Fmerchandising_Mobile%2BNew%2BLaunches_NA_dealCard_cc_1_NA_view-all_5&lid=LSTMOBFFVFDJCCUZDGTTDBWU8&fm=neo%2Fmerchandising&iid=b570f7a7-6ac8-47ca-8c45-4faa4bf29b96.MOBFFVFDJCCUZDGT.SEARCH&ppt=browse&ppn=browse&ssid=gk6ysivyc00000001565692482177";

    public static void main(String[] args) throws Exception {
        CrawlerConfig crawlerConfig = new DefaultProxyCrawlerConfig("Pradyumn-Test-App", new RandomUserAgentManager(UserAgentType.DESKTOP));
        System.out.println("asbdckhsdbckbsdkcbds");
        PrintWriter out = null;

        SyncCrawler syncCrawler = new SyncCrawler(crawlerConfig);
        Parser parser = new FlipkartParser();

        Queue<String> queue = new ArrayDeque<>();
        Set<String> set = new HashSet<>();
        set.add(LINK_TO_WEBSITE);
        queue.add(LINK_TO_WEBSITE);
        int counter = 0;

        while (!queue.isEmpty()) {
            String currentLink = queue.poll();
            set.add(currentLink);
            try {
                SyncCrawlResponse response = syncCrawler.crawl(currentLink);
                String content = response.getContent();
                SiteDataParsed dataParsed = null;
                dataParsed = parser.parseData(content, currentLink);
                out = new PrintWriter(new FileOutputStream(PATH + "mobiles/output" + counter + ".json"));
                out.println(dataParsed.getJson());
                out.flush();
                out.close();

                for (String link : dataParsed.getLinks()) {
                    if (set.contains(link)) continue;
                    set.add(link);
                    queue.add(link);
                }
                ++counter;
            } catch (Exception e) {
                System.err.println("Website not parsable");
            }
            if (counter == 20) break;
        }
    }

    public static final boolean isValidLinkF(String link) {
        if (link == null || link.length() == 0) return false;
        if (!link.contains("www.") || !link.contains("flipkart")) return false;
        return true;
    }
}
