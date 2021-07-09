package flipkart;

import bean.MobilePhoneBean;
import clients.CrawlClient;
import clients.DBClient;
import clients.PrintStreamClient;
import dataproc.AmazonDataCrawler;
import net.media.mnetcrawler.SyncCrawler;
import net.media.mnetcrawler.bean.SyncCrawlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parsers.FlipkartParser;
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
import java.util.List;

import static constants.Constants.EMPTY_STRING;
import static constants.Constants.MATCHER;
import static constants.Constants.SPACE_SYMBOL;
import static constants.Constants.USE_MNET_CRAWLER;

public class FlipkartDataCrawler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(AmazonDataCrawler.class);
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
    private PrintWriter out;

    /**
     * CALL INSERT_MOBILE(?, ?, ?, ?, ?, ?, ?);
     * CALL SEARCH_MOBILE(?);
     *
     * @param args
     */

    public static void main(String[] args) {
        new Thread(null, new FlipkartDataCrawler(), ":)", Runtime.getRuntime().maxMemory() / 3).start();

        /**
         * 1. fetch all mobiles list
         * 2. for each mobile fetch links from google for each store
         * 3. go to site and check for mobile validity
         * 4. if mobile parse data and create data insertion bean
         * TODO:
         * 5. insert in DB Table using PROCs
         */
    }

    @Override
    public void run() {
        try {
            BuildEnvironment.build();
            initIO();
            List<String> allLinks = fetchAllLinks();

            for (String currentURL : allLinks) {
                try {
                    System.out.println("Processing URL : " + currentURL);
                    processLink(currentURL, 0);
                    out.println(currentURL);
                    out.flush();
                } catch (Exception e) {
                    System.err.println("Failed for liunk : " + currentURL);
                    e.printStackTrace(PrintStreamClient.getClient());
                }
            }
            out.close();
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private List<String> fetchAllLinks() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/flipkart/links.txt")));
        List<String> links = new ArrayList<>();
        while (bufferedReader.ready()) {
            links.add(bufferedReader.readLine());
        }
        return links;
    }

    private void initIO() throws FileNotFoundException {
        out = new PrintWriter(new FileOutputStream(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/flipkart/done_links.txt"), true));
    }


    private void processLink(final String link, int storeID) throws Exception {
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
        if (storeID == 0) {
            mobilePhoneBean = new FlipkartParser().parseData(pageSource);
        } else {
            throw new RuntimeException();
        }
        if (mobilePhoneBean != null && mobilePhoneBean.isPhone()) {
            mobilePhoneBean.setMobileStoreLink(link);
            mobilePhoneBean.setImageURL(null);
            try {
                PreparedStatement preparedStatement = getPreparedStatementFromBean(mobilePhoneBean, MATCHER[storeID]);
                boolean status = preparedStatement.execute();
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
                throw e;
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
}

