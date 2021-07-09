package clients;

import net.media.mnetcrawler.CrawlerConfig;
import net.media.mnetcrawler.DefaultProxyCrawlerConfig;
import net.media.mnetcrawler.SyncCrawler;
import net.media.mnetcrawler.bean.UserAgentType;
import net.media.mnetcrawler.util.RandomUserAgentManager;

import static constants.Constants.CRAWLER_APP_NAME;

/**
 *
 */
public class CrawlClient {
    private static SyncCrawler syncCrawler;

    public static SyncCrawler getCrawlClient() throws Exception {
        if (syncCrawler == null) {
            synchronized (SyncCrawler.class) {
                if (syncCrawler == null) {
                    syncCrawler = createClient();
                }
            }
        }
        return syncCrawler;
    }

    private static SyncCrawler createClient() throws Exception {
        CrawlerConfig crawlerConfig = new DefaultProxyCrawlerConfig(CRAWLER_APP_NAME, new RandomUserAgentManager(UserAgentType.DESKTOP));
        syncCrawler = new SyncCrawler(crawlerConfig);
        return syncCrawler;
    }
}