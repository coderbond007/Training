package crawler;

import bean.GoogleSearchQueryData;
import clients.CrawlClient;
import clients.PrintStreamClient;
import clients.SeleniumCrawlClient;
import net.media.mnetcrawler.SyncCrawler;
import net.media.mnetcrawler.bean.SyncCrawlResponse;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.StringJoiner;

import static constants.Constants.ADD_SYMBOL;
import static constants.Constants.EMPTY_STRING;
import static constants.Constants.SPACE_SYMBOL;
import static constants.Constants.USE_MNET_CRAWLER;
import static constants.Constants.ZERO;

public class GoogleCrawler {

    private static final String GOOGLE_SEARCH_QUERY = "https://www.google.com/search?q=";
    private static final String SITE_LINK = "+site%3A";

    private static final String[] RESULT_BLOCK_TAGS = new String[]{"kCrYT", "r"};
    private static final String HREF_FOR_CSS_QUERY = "a[href]";
    private static final String HREF_ATTRIBUTE_KEY = "abs:href";
    private static final SplittableRandom gen = new SplittableRandom();

    public static List<String> fetchSearchResults(final GoogleSearchQueryData queryData) throws Exception {
        if (!queryData.isValid()) {
            return null;
        }
        final String query = createGoogleSearchQuery(queryData);
        String pageContent = null;
        if (USE_MNET_CRAWLER) {
            pageContent = getPageContentSyncCrawler(query);
        } else {
            pageContent = getPageContentWebDriver(query);
        }
        final List<String> allLinks = getResultsLinks(pageContent);
        return allLinks;
    }

    /**
     * @param pageContent
     * @return
     */
    private static List<String> getResultsLinks(final String pageContent) {
        if (StringUtil.isBlank(pageContent)) return null;
        List<String> URLs = new ArrayList<>();
        Document document = Jsoup.parse(pageContent);
        for (final String RESULT_BLOCK_TAG : RESULT_BLOCK_TAGS) {
            Elements blocks = document.getElementsByClass(RESULT_BLOCK_TAG);
            if (blocks.size() == 0) continue;
            for (final Element element : blocks) {
                try {
                    String currentURL = element
                            .select(HREF_FOR_CSS_QUERY)
                            .first()
                            .attr(HREF_ATTRIBUTE_KEY);
//                    String name = element
//                            .select("div.BNeawe.vvjwJb.AP7Wnd")
//                            .first()
//                            .text()
//                            .toLowerCase();
                    if (StringUtil.isBlank(currentURL)) continue;
//                    if (name.contains("renewed") || name.contains("refurbished")) continue;
                    if (URLs.size() >= 5) break;
                    URLs.add(currentURL);
                } catch (Exception e) {
                    try {
                        e.printStackTrace(PrintStreamClient.getClient());
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return URLs.size() == ZERO ? null : URLs;
    }

    /**
     * @param query
     * @return
     * @throws Exception
     */
    private static String getPageContentSyncCrawler(final String query) throws Exception {
        final SyncCrawler syncCrawler = CrawlClient.getCrawlClient();
        final SyncCrawlResponse syncCrawlResponse = syncCrawler.crawl(query);
        String pageSource = syncCrawlResponse.getContent();
        if (pageSource == null) pageSource = EMPTY_STRING;
        return pageSource;
    }

    private static String getPageContentWebDriver(final String query) throws Exception {
        final WebDriver webDriver = SeleniumCrawlClient.getWebDriverClient();
        webDriver.get(query);
        webDriver.manage().window().maximize();
        String pageSource = webDriver.getPageSource();
        if (pageSource == null) pageSource = EMPTY_STRING;
        randomSleep();
        webDriver.quit();
        return pageSource;
    }

    /**
     * @param queryData
     * @return
     */
    private static String createGoogleSearchQuery(final GoogleSearchQueryData queryData) {
        StringBuilder currentLink = new StringBuilder(GOOGLE_SEARCH_QUERY);
        StringJoiner keywordString = new StringJoiner(ADD_SYMBOL);
        for (String currentKeyword : queryData.getSearchKeywords()) {
            String[] parts = currentKeyword.split(SPACE_SYMBOL);
            for (String part : parts) {
                keywordString.add(part);
            }
        }
        currentLink.append(keywordString);
        if (queryData.getSite().length() != ZERO) {
            currentLink.append(SITE_LINK);
            currentLink.append(queryData.getSite());
        }
        return currentLink.toString();
    }

    public static void randomSleep() throws InterruptedException {
        int timeOut = gen.nextInt(1500) + gen.nextInt(1500);
        while (timeOut < 2000) ++timeOut;
        Thread.sleep(timeOut);
    }
}
