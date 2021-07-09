package dataproc;

import bean.GoogleSearchQueryData;
import bean.MobilePhoneBean;
import clients.CrawlClient;
import clients.DBClient;
import clients.PrintStreamClient;
import crawler.GoogleCrawler;
import net.media.mnetcrawler.SyncCrawler;
import net.media.mnetcrawler.bean.SyncCrawlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parsers.AmazonParserModified;
import parsers.FlipkartParser;
import parsers.PaytmMallParser;
import parsers.SnapDealParser;
import server.BuildEnvironment;
import util.SeleniumCrawlUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static constants.Constants.EMPTY_STRING;
import static constants.Constants.MATCHER;
import static constants.Constants.MOBILE_NAMES_PATH;
import static constants.Constants.PATH;
import static constants.Constants.SPACE_SYMBOL;
import static constants.Constants.STORES;
import static constants.Constants.USE_MNET_CRAWLER;
import static constants.Constants.ZERO;

public class AmazonDataCrawler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(AmazonDataCrawler.class);
    private static final String ALL_DATA_PATH = PATH + "/data/alldata/";
    private static final String DATA = "data";
    private static final String JSON = ".json";
    private static final String INSERT_MOBILE_PROC = "CALL INSERT_MOBILE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final int MOBILE_NAME_COMPRESSED_NOW_INDEX = 1;
    private static final int RAM_NOW_INDEX = 2;
    private static final int COLOR_NOW_INDEX = 3;
    private static final int ACTUAL_MOBILE_NAME_NOW_INDEX = 4;
    private static final int SELLER_NAME_NOW_INDEX = 5;
    private static final int PRICE_NOW_INDEX = 6;
    private static final int MOBILE_LINK_NOW_INDEX = 7;
    private static final int IMAGE_URL_NOW_INDEX = 8;
    private static final int BRAND_NOW_INDEX = 9;
    private static final int AVAILABLE_STATUS_NOW_INDEX = 10;

    private static final int LIMIT = 4000;
    private int counter = 0;
    private List<String> mobileNames = new ArrayList<>();
    private Set<String> alreadyDone;
    private PrintWriter out;

    /**
     * CALL INSERT_MOBILE(?, ?, ?, ?, ?, ?, ?);
     * CALL SEARCH_MOBILE(?);
     *
     * @param args
     */

    public static void main(String[] args) {
        new Thread(null, new AmazonDataCrawler(), ":)", Runtime.getRuntime().maxMemory() / 3).start();
    }

    @Override
    public void run() {
        try {
            BuildEnvironment.build();
            buildEnv();
            fetchAllMobileNames();
            processEachMobile();
            out.close();
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void buildEnv() throws IOException, SQLException {
        alreadyDone = new HashSet<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/data/done_amazon.txt")));
        while (bufferedReader.ready()) {
            alreadyDone.add(bufferedReader.readLine());
        }
        bufferedReader.close();

        out = new PrintWriter(new FileOutputStream(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/data/done_amazon.txt"), true));
    }

    private void processEachMobile() throws Exception {
//        for (int currentIndex = 0; currentIndex < MATCHER.length; ++currentIndex) {
//            final String currentStore = STORES[currentIndex];
//            final String currentMatcher = MATCHER[currentIndex];
//            if (currentMatcher.equals("amazon")) continue;
//            for (final String currentMobileName : mobileNames) {
//                final String[] searchKeywords = currentMobileName.split(SPACE_SYMBOL);
//                GoogleSearchQueryData queryData = new GoogleSearchQueryData(currentStore, searchKeywords);
//                System.out.println("Created Google Query");
//                List<String> resultsLinks = GoogleCrawler.fetchSearchResults(queryData);
//                if (resultsLinks == null) continue;
//                System.out.println("Starting for mobile : " + currentMobileName + " and site : " + currentStore);
//                List<String> filteredResultsLink = filterLinks(resultsLinks, currentMatcher);
//                processResultsLink(currentStore, currentMobileName, filteredResultsLink, currentIndex);
//            }
//        }

        for (final String currentMobileName : mobileNames) {
            if (alreadyDone.contains(currentMobileName)) continue;
            for (int currentIndex = 0; currentIndex < MATCHER.length; ++currentIndex) {
                final String currentStore = STORES[currentIndex];
                final String currentMatcher = MATCHER[currentIndex];
                if (!currentMatcher.equals("amazon")) continue;
                try {
                    final String[] searchKeywords = currentMobileName.split(SPACE_SYMBOL);
                    GoogleSearchQueryData queryData = new GoogleSearchQueryData(currentStore, searchKeywords);
                    System.out.println("Created Google Query");
                    List<String> resultsLinks = GoogleCrawler.fetchSearchResults(queryData);
                    if (resultsLinks == null) continue;
                    System.out.println("Starting for mobile : " + currentMobileName + " and site : " + currentStore);
                    List<String> filteredResultsLink = filterLinks(resultsLinks, currentMatcher);
                    processResultsLink(currentStore, currentMobileName, filteredResultsLink, currentIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            out.println(currentMobileName);
            out.flush();
        }
    }

    private List<String> filterLinks(final List<String> resultsLinks, final String matcher) {
        List<String> links = new ArrayList<>();
        for (final String currentLink : resultsLinks) {
            if (currentLink.contains(matcher)) links.add(currentLink);
        }
        return links;
    }

    private void processResultsLink(final String store, final String mobileName, final List<String> resultsLinks, int storeID) throws Exception {
        if (resultsLinks == null || resultsLinks.size() == ZERO)
            return;
        for (final String currentResLink : resultsLinks) {
            try {
                processLink(store, mobileName, currentResLink, storeID);
            } catch (Exception e) {
                try {
                    e.printStackTrace(PrintStreamClient.getClient());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void processLink(final String store, final String mobileName, final String link, int storeID) throws Exception {
//        File fileAppender = new File(ALL_DATA_PATH + mobileName + SLASH_SYMBOL + "temp.txt");
//        fileAppender.getParentFile().mkdirs();
//        PrintWriter appender = new PrintWriter(new FileOutputStream(fileAppender, true));
//        appender.println(link);
//        appender.close();
        String pageSource = null;
        try {
            if (USE_MNET_CRAWLER) {
                final SyncCrawler crawler = CrawlClient.getCrawlClient();
                SyncCrawlResponse syncCrawlResponse = crawler.crawl(link);
                pageSource = syncCrawlResponse.getContent();
            } else {
                pageSource = SeleniumCrawlUtil.getPageSource(link);
            }
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            throw e;
        }
        if (pageSource == null)
            return;
        MobilePhoneBean mobilePhoneBean = null;
        switch (storeID) {
            case 0:
                mobilePhoneBean = new FlipkartParser().parseData(pageSource);
                break;
            case 1:
                mobilePhoneBean = new AmazonParserModified().parseData(pageSource);
                break;
            case 2:
                mobilePhoneBean = new PaytmMallParser().parseData(pageSource);
                break;
            case 3:
                mobilePhoneBean = new SnapDealParser().parseData(pageSource);
                break;
            default:
                throw new RuntimeException();
        }
        if (mobilePhoneBean != null && mobilePhoneBean.isPhone()) {
            mobilePhoneBean.setMobileStoreLink(link);
            mobilePhoneBean.setImageURL(null);
//            System.out.println(GsonClient.getClient().toJson(mobilePhoneBean));
//            File file = new File(ALL_DATA_PATH + mobileName + SLASH_SYMBOL + store + SLASH_SYMBOL + DATA + counter + JSON);
//            ++counter;
//            System.out.println("---------------------" + link + "----------------------");
//            file.getParentFile().mkdirs();
//            mobilePhoneBean.setMobileStoreLink(link);
//            PrintWriter out = new PrintWriter(new FileOutputStream(file));
//            Gson gson = GsonClient.getClient();
//            out.println(gson.toJson(mobilePhoneBean));
//            out.close();
            try {
                PreparedStatement preparedStatement = getPreparedStatementFromBean(mobilePhoneBean, MATCHER[storeID]);
                boolean status = preparedStatement.execute();
                System.out.println("Init");
                if (!status) {
                    logger.error("Couldn't execute");
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
    private PreparedStatement getPreparedStatementFromBean(final MobilePhoneBean phoneBean, String storeNameInDB) throws SQLException, IOException {
        logger.info("Inside the getPreparedStatementFromBean");
        final long startTime = System.currentTimeMillis();
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
        final long endTime = System.currentTimeMillis();
        logger.info("Time Taken for the getPreparedStatementFromBean : " + (endTime - startTime) + "ms");
        return preparedStatement;
    }

    private void fetchAllMobileNames() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(MOBILE_NAMES_PATH)));
        int counter = 0;
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            mobileNames.add(line);
            ++counter;
            if (counter == LIMIT) break;
        }
        bufferedReader.close();
    }
}
