package crawler;

import Bean.SiteDataParsed;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import net.media.mnetcrawler.CrawlerConfig;
import net.media.mnetcrawler.DefaultProxyCrawlerConfig;
import net.media.mnetcrawler.SyncCrawler;
import net.media.mnetcrawler.bean.SyncCrawlResponse;
import net.media.mnetcrawler.bean.UserAgentType;
import net.media.mnetcrawler.util.RandomUserAgentManager;
import parsers.FlipkartParser;
import parsers.Parser;

public class Sample {

  public static final String PATH = System.getProperty("user.dir") + "/data/";
  public static final String LINK_TO_WEBSITE = "https://www.flipkart.com/realme-3-dynamic-black-64-gb/p/itmfe68wrbfnzqwz?pid=MOBFFVFDJCCUZDGT&srno=b_1_1&otracker=clp_omu_Mobile%2BNew%2BLaunches_1_5.dealCard.OMU_mobile-phones-store_mobile-phones-store_X95WZDUDIR3U_5&otracker1=clp_omu_PINNED_neo%2Fmerchandising_Mobile%2BNew%2BLaunches_NA_dealCard_cc_1_NA_view-all_5&lid=LSTMOBFFVFDJCCUZDGTTDBWU8&fm=neo%2Fmerchandising&iid=b570f7a7-6ac8-47ca-8c45-4faa4bf29b96.MOBFFVFDJCCUZDGT.SEARCH&ppt=browse&ppn=browse&ssid=gk6ysivyc00000001565692482177";
//    public static final Logger logger = LoggerFactory.getLogger(Sample.class);

  public static void main(String[] args) throws Exception {
    CrawlerConfig crawlerConfig = new DefaultProxyCrawlerConfig("Jayant-Test-App",
        new RandomUserAgentManager(UserAgentType.DESKTOP));
    SyncCrawler syncCrawler = new SyncCrawler(crawlerConfig);
    SyncCrawlResponse response = syncCrawler.crawl(LINK_TO_WEBSITE);
    String content = response.getContent();

    PrintWriter out = new PrintWriter(new FileOutputStream(PATH + "output.html"), false);
    out.println(response.getContent());
    out.flush();
    out.close();

//    Parser parser = new FlipkartParser();
//    SiteDataParsed dataParsed = parser.parseData(content, LINK_TO_WEBSITE);
//    out = new PrintWriter(new FileOutputStream(PATH + "output.json"), false);
//    out.println(dataParsed.getJson());
//    System.out.println(dataParsed.getLinks());
//    out.flush();
//    out.close();
  }

//    public static final void parser() {
//        PrintWriter out = null;
//        WebDriver webDriver = null;
//        try {
//            out = new PrintWriter(new FileOutputStream(PATH + "output.txt", false));
//            webDriver = new ChromeDriver();
//            // Navigate to URL
//            webDriver.get(LINK_TO_WEBSITE);
//            webDriver.manage().window().maximize();
//            String pageSource = webDriver.getPageSource();
//            String json = getDataFlipkartJSON(pageSource);
//            out.println(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            webDriver.quit();
//            out.close();
//        }
//    }
//
//    public static final SiteDataParsed getDataFlipkartJSON(String pageSource) {
//        Document doc = Jsoup.parse(pageSource);
//        Element allTables = doc.getElementsByClass("_3Rrcbo").first();
//        Elements tables = allTables.getElementsByClass("_2RngUh");
//        Map<String, String> features = new HashMap<>();
//        for (Element currentTable : tables) {
//            Elements allRows = currentTable.select("tr._3_6Uyw.row");
//            for (Element row : allRows) {
//                String key = row.select("td._3-wDH3.col.col-3-12").first().text();
//                String value = row.getElementsByClass("_3YhLQA").first().text();
//                System.out.println(key + " => " + value);
//                features.put(key.toLowerCase(), value.toLowerCase());
//            }
//        }
//        Gson gson = new Gson();
//        String json = gson.toJson(features);
//        return json;
//    }
//
//    public static final List<String> getAllChildLinks(String pageSource) {
//        List<String> links = new ArrayList<>();
//        Document doc = Jsoup.parse(pageSource);
//    }
}