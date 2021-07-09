package crons;

import bean.MobileDetails;
import bean.MobileDetailsResult;
import bean.MobilePhoneBean;
import clients.CrawlClient;
import clients.DBClient;
import clients.PrintStreamClient;
import net.media.mnetcrawler.SyncCrawler;
import net.media.mnetcrawler.bean.SyncCrawlResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parsers.AmazonParserModified;
import search.FetchMobileWithID;
import util.SeleniumCrawlUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static constants.Constants.EMPTY_STRING;
import static constants.Constants.MATCHER;
import static constants.Constants.SPACE_SYMBOL;
import static constants.Constants.USE_MNET_CRAWLER;
import static dataproc.DBFiller.ACTUAL_MOBILE_NAME_NOW_INDEX;
import static dataproc.DBFiller.AVAILABLE_STATUS_NOW_INDEX;
import static dataproc.DBFiller.BRAND_NOW_INDEX;
import static dataproc.DBFiller.COLOR_NOW_INDEX;
import static dataproc.DBFiller.IMAGE_URL_NOW_INDEX;
import static dataproc.DBFiller.INSERT_MOBILE_PROC;
import static dataproc.DBFiller.MOBILE_LINK_NOW_INDEX;
import static dataproc.DBFiller.MOBILE_NAME_COMPRESSED_NOW_INDEX;
import static dataproc.DBFiller.PRICE_NOW_INDEX;
import static dataproc.DBFiller.RAM_NOW_INDEX;
import static dataproc.DBFiller.SELLER_NAME_NOW_INDEX;

public class TrendingCron {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final String BEST_SELLER_PHONES = "https://www.amazon.in/gp/bestsellers/electronics/1389432031";
    private static List<MobileDetails> trendingPhonesCaches = new ArrayList<>();

    public static void startScheduledTask() {
        final ScheduledFuture<?> taskHandle = scheduler.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            updateTopTrendingMobiles();
                        } catch (Exception e) {
                            try {
                                e.printStackTrace(PrintStreamClient.getClient());
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }, 0, 1, TimeUnit.DAYS);
    }

    private static void updateTopTrendingMobiles() throws Exception {
        try {
            String pageSource =
                    CrawlClient
                            .getCrawlClient()
                            .crawl(BEST_SELLER_PHONES)
                            .getContent();
            List<String> links = fetchAllLinks(pageSource);
            List<Long> ids = fetchIDsOfNewPhones(links);
            for (Long currentID : ids) {
                MobileDetailsResult mobileDetailsResult = FetchMobileWithID.fetchAllMobiles(currentID);
                trendingPhonesCaches.addAll(mobileDetailsResult.getList());
            }
        } catch (Exception e) {
            e.printStackTrace(PrintStreamClient.getClient());
            throw e;
        }
    }

    private static List<Long> fetchIDsOfNewPhones(final List<String> links) {
        List<Long> ids = new ArrayList<>();

        for (String currentLink : links) {
            try {
                Long nowID = getIDOfPhone(currentLink);
                if (nowID != null) {
                    ids.add(nowID);
                }
            } catch (Exception e) {
                try {
                    e.printStackTrace(PrintStreamClient.getClient());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return ids;
    }

    private static Long getIDOfPhone(final String URL) {
        String pageSource = null;
        try {
            if (USE_MNET_CRAWLER) {
                final SyncCrawler crawler = CrawlClient.getCrawlClient();
                SyncCrawlResponse syncCrawlResponse = crawler.crawl(URL);
                pageSource = syncCrawlResponse.getContent();
            } else {
                pageSource = SeleniumCrawlUtil.getPageSource(URL);
            }
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (pageSource == null)
            return null;
        MobilePhoneBean mobilePhoneBean = new AmazonParserModified().parseData(pageSource);
        Long id = null;
        if (mobilePhoneBean != null && mobilePhoneBean.isPhone()) {
            mobilePhoneBean.setMobileStoreLink(URL);
            mobilePhoneBean.setImageURL(null);
            try {
                PreparedStatement preparedStatement = getPreparedStatementFromBean(mobilePhoneBean, MATCHER[1]);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    id = resultSet.getLong("mobile_id");
                }
                resultSet.close();
                if (id == null) {
                    System.err.println("Couldn't execute");
                } else {
                    System.out.println("Successfully executed");
                }
            } catch (Exception e) {
                try {
                    e.printStackTrace(PrintStreamClient.getClient());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return id;
    }

    /**
     * getPreparedStatementFromBean prepares the PreparedStatement for insert query
     * <p>
     * MOBILE_NAME_COMPRESSED_NOW
     * RAM_NOW
     * COLOR_NOW
     * ACTUAL_MOBILE_NAME_NOW
     * SELLER_NAME_NOW
     * PRICE_NOW
     * MOBILE_LINK_NOW
     * </p>
     */
    private static PreparedStatement getPreparedStatementFromBean(final MobilePhoneBean phoneBean, String storeNameInDB) throws SQLException, IOException {
        PreparedStatement preparedStatement = DBClient.getPreparedStatement(INSERT_MOBILE_PROC);
        final String mobileName = phoneBean.getGenericName();
        preparedStatement.setString(MOBILE_NAME_COMPRESSED_NOW_INDEX, mobileName.replace(SPACE_SYMBOL, EMPTY_STRING).trim());
        preparedStatement.setString(RAM_NOW_INDEX, phoneBean.getRam());
        preparedStatement.setString(COLOR_NOW_INDEX, phoneBean.getColor());
        preparedStatement.setString(ACTUAL_MOBILE_NAME_NOW_INDEX, phoneBean.getModelName());
        preparedStatement.setString(SELLER_NAME_NOW_INDEX, storeNameInDB);
        preparedStatement.setInt(PRICE_NOW_INDEX, phoneBean.getPrice());
        preparedStatement.setString(MOBILE_LINK_NOW_INDEX, phoneBean.getMobileStoreLink());
        preparedStatement.setString(IMAGE_URL_NOW_INDEX, phoneBean.getImageURL());
        preparedStatement.setString(BRAND_NOW_INDEX, phoneBean.getBrandName());
        preparedStatement.setInt(AVAILABLE_STATUS_NOW_INDEX, phoneBean.getAvailableStatus() ? 1 : 0);
        return preparedStatement;
    }

    private static List<String> fetchAllLinks(String pageSource) {
        List<String> links = new ArrayList<>();
        Document document = Jsoup.parse(pageSource);
        Elements elements = document.getElementsByClass("zg-item-immersion");
        for (Element currentBlock : elements) {
            try {
                Elements nowLinks = currentBlock.select("a[href]");
                for (Element nowLink : nowLinks) {
                    links.add(nowLink.attr("href"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return links;
    }

    public static List<MobileDetails> getTrendingPhonesCaches() throws Exception {
        if (trendingPhonesCaches == null)
            updateTopTrendingMobiles();
        return trendingPhonesCaches;
    }
}
