package crons;

import basicparsers.AbstractSimpleParser;
import basicparsers.AmazonSimpleParser;
import basicparsers.FlipkartSimpleParser;
import basicparsers.PaytmMallSimpleParser;
import basicparsers.SnapDealSimpleParser;
import clients.CrawlClient;
import clients.DBClient;
import clients.PrintStreamClient;
import server.BuildEnvironment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PriceUpdater {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final String MOBILE_ID_KEY = "mobile_id";
    private static final String SELLER_ID_KEY = "seller_id";
    private static final String PRICE_KEY = "price";
    private static final String AVAILABLE_STATUS_KEY = "available_status";
    private static final String MOBILE_LINK_KEY = "mobile_link";
    private static final String FETCH_DATA_QUERY = "SELECT mobile_id, seller_id, price, available_status, mobile_link FROM MOBILE_SELLER_DETAILS;";
    private static final String UPDATE_DATA_QUERY = "UPDATE MOBILE_SELLER_DETAILS SET available_status = ?, price = ? WHERE mobile_id = ? AND seller_id = ?;";

    public static void main(String[] args) {
        PriceUpdater.startScheduledTask();
    }

    public static void startScheduledTask() {
        final ScheduledFuture<?> taskHandle = scheduler.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            updateEntries();
                            BuildEnvironment.loadListForFuzzySearch();
                        } catch (Exception e) {
                            try {
                                e.printStackTrace(PrintStreamClient.getClient());
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }, 0, 3, TimeUnit.HOURS);
    }

    private static PrintWriter updLog;

    static {
        try {
            updLog = new PrintWriter(new FileOutputStream(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/crons/updates.txt"), true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void updateEntries() throws IOException, SQLException {
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/crons/cronLog.log"), true));
        final long startTime = System.currentTimeMillis();
        try {
            List<RowEntry> rowEntries = getAllEntries();
            crawlAndUpdateBeans(rowEntries);
//            updateDBEntries(rowEntries);
            System.out.println("Completed updation");
        } catch (Exception e) {
            out.println("Failed to execute CRON Job");
            out.flush();
        }
        final long endTime = System.currentTimeMillis();
        out.println("Time Taken for CRON Job : " + (endTime - startTime) + "ms");
        out.flush();
        out.close();
        updLog.flush();
        updLog.close();
    }

    private static void updateDBEntries(final List<RowEntry> rowEntries) throws IOException, SQLException {
        if (rowEntries == null || rowEntries.size() == 0)
            return;
        for (RowEntry currentEntry : rowEntries) {
            try {
                if (
                        currentEntry.newAvailableStatus == null ||
                                currentEntry.newPrice == null ||
                                currentEntry.mobileId == null ||
                                currentEntry.sellerId == null
                ) {
                    continue;
                }
                updateDBEntry(currentEntry);
            } catch (Exception e) {
                e.printStackTrace(PrintStreamClient.getClient());
            }
        }
    }

    private static void updateDBEntry(RowEntry currentEntry) {
        try {
            if (
                    currentEntry.newAvailableStatus == null ||
                            currentEntry.newPrice == null ||
                            currentEntry.mobileId == null ||
                            currentEntry.sellerId == null
            ) return;
            PreparedStatement preparedStatement = DBClient.getPreparedStatement(UPDATE_DATA_QUERY);
            preparedStatement.setInt(1, currentEntry.newAvailableStatus);
            preparedStatement.setLong(2, currentEntry.newPrice);
            preparedStatement.setLong(3, currentEntry.mobileId);
            preparedStatement.setLong(4, currentEntry.sellerId);
            boolean executeQuery = false;
            if (!currentEntry.newAvailableStatus.equals(currentEntry.availableStatus)) {
                executeQuery = true;
            }
            if (!currentEntry.newPrice.equals(currentEntry.price)) {
                executeQuery = true;
            }
            if (executeQuery) {
                System.out.println("Changed observed");
                try {
                    preparedStatement.execute();
                    updLog.println("Updated DB with rowEntry : " + currentEntry.toString());
                    updLog.flush();
                    System.out.println("Executed");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void crawlAndUpdateBeans(List<RowEntry> rowEntries) throws Exception {
        if (rowEntries == null || rowEntries.size() == 0)
            return;
        for (RowEntry currentEntry : rowEntries) {
            try {
                if (currentEntry.sellerId == 5)
                    continue;
                String pageSource = CrawlClient.getCrawlClient().crawl(currentEntry.mobileLink).getContent();
                System.out.println("Crawling data :" + currentEntry.mobileLink);
                AbstractSimpleParser simpleParser = null;
                switch (currentEntry.sellerId.intValue()) {
                    case 1:
                        simpleParser = new AmazonSimpleParser();
                        break;
                    case 2:
                        simpleParser = new FlipkartSimpleParser();
                        break;
                    case 3:
                        simpleParser = new PaytmMallSimpleParser();
                        break;
                    case 4:
                        simpleParser = new SnapDealSimpleParser();
                        break;
                    default:
                        throw new RuntimeException();
                }
                simpleParser.parseData(pageSource);
                currentEntry.newPrice = simpleParser.getPriceUpdated();
                currentEntry.newAvailableStatus = simpleParser.getAvailabilityUpdated() ? 1 : 0;
                if (currentEntry.newPrice == null) currentEntry.newPrice = currentEntry.price;
                updateDBEntry(currentEntry);
            } catch (Exception e) {
                e.printStackTrace(PrintStreamClient.getClient());
            }
        }
        System.out.println("Carwled all data for cron");
    }

    private static List<RowEntry> getAllEntries() throws IOException, SQLException {
        List<RowEntry> rowEntries = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBClient.getPreparedStatement(FETCH_DATA_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long mobileId = resultSet.getLong(MOBILE_ID_KEY);
                Long sellerId = resultSet.getLong(SELLER_ID_KEY);
                Long price = resultSet.getLong(PRICE_KEY);
                Integer availableStatus = resultSet.getInt(AVAILABLE_STATUS_KEY);
                String mobileLink = resultSet.getString(MOBILE_LINK_KEY);
                RowEntry rowEntry = new RowEntry(mobileId, sellerId, price, availableStatus, mobileLink);
                if (sellerId == 5) continue;
                rowEntries.add(rowEntry);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        System.out.println("Fetched all entries of size : " + rowEntries.size());
        return rowEntries;
    }

    static class RowEntry {
        Long mobileId;
        Long sellerId;
        Long price;
        Integer availableStatus;
        String mobileLink;

        Long newPrice;
        Integer newAvailableStatus;

        public RowEntry(Long mobileId, Long sellerId, Long price, Integer availableStatus, String mobileLink) {
            this.mobileId = mobileId;
            this.sellerId = sellerId;
            this.price = price;
            this.availableStatus = availableStatus;
            this.mobileLink = mobileLink;
        }

        @Override
        public String toString() {
            return "RowEntry{" +
                    "mobileId=" + mobileId +
                    ", sellerId=" + sellerId +
                    ", price=" + price +
                    ", availableStatus=" + availableStatus +
                    ", mobileLink='" + mobileLink + '\'' +
                    ", newPrice=" + newPrice +
                    ", newAvailableStatus=" + newAvailableStatus +
                    '}';
        }
    }
}
