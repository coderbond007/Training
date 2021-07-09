package dataproc;

import bean.MobilePhoneBean;
import clients.DBClient;
import clients.PrintStreamClient;
import org.jsoup.helper.StringUtil;
import parsers.AmazonParserModified;
import parsers.FlipkartParser;
import parsers.PaytmMallParser;
import parsers.SnapDealParser;
import server.BuildEnvironment;
import util.SeleniumCrawlUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import static constants.Constants.MATCHER;
import static crawler.GoogleCrawler.randomSleep;

public class ImageFiller implements Runnable {
    private static final SplittableRandom gen = new SplittableRandom(System.currentTimeMillis());
    private static final String UPDATE_IMAGE = "UPDATE MOBILE_DETAILS SET image_url = ? WHERE id = ? and image_url is null;";
    private static List<Datum> data;

    public static void main(String[] args) {
        new Thread(null, new ImageFiller(), ":)", Runtime.getRuntime().maxMemory() >> 1).start();
    }

    @Override
    public void run() {
        try {
            BuildEnvironment.build();
            fetchIDs();
            processImages();
        } catch (IOException | SQLException e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void processImages() {
        for (Datum currentData : data) {
            for (int currentIndex = 0; currentIndex < MATCHER.length - 1; ++currentIndex) {
                final String currentMatcher = MATCHER[currentIndex];
                if (currentData.hitURL.contains(currentMatcher)) {
                    try {
                        processCurrentData(currentData, currentIndex);
                    } catch (Exception e) {
                        try {
                            e.printStackTrace(PrintStreamClient.getClient());
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void processCurrentData(final Datum currentData, final int currentIndex) throws IOException, SQLException {
        String pageSource = null;
        try {
            pageSource = SeleniumCrawlUtil.getPageSource(currentData.hitURL);
            randomSleep();
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        MobilePhoneBean mobilePhoneBean = null;
        switch (currentIndex) {
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
                throw new IllegalStateException();
        }
        assert mobilePhoneBean != null;
        if (!StringUtil.isBlank(mobilePhoneBean.getImageURL())) {
            currentData.imageURL = mobilePhoneBean.getImageURL();
            PreparedStatement preparedStatement = DBClient.getPreparedStatement(UPDATE_IMAGE);
            preparedStatement.setString(1, currentData.imageURL);
            preparedStatement.setLong(2, currentData.id);
            preparedStatement.executeUpdate();
        }
    }

    private void fetchIDs() throws IOException, SQLException {
        data = new ArrayList<>();
        PreparedStatement preparedStatement = DBClient.getPreparedStatement("SELECT A.id as id, MAX(B.mobile_link) as mobile_link FROM MOBILE_DETAILS A JOIN MOBILE_SELLER_DETAILS B ON A.id = B.mobile_id and A.image_url is null GROUP BY A.id;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Datum datum = new Datum();
            datum.hitURL = resultSet.getString("mobile_link");
            datum.id = resultSet.getLong("id");
            data.add(datum);
        }
        resultSet.close();
    }

    static class Datum {
        long id;
        String hitURL;
        String imageURL;

        @Override
        public String toString() {
            return "Datum{" +
                    "id=" + id +
                    ", hitURL='" + hitURL + '\'' +
                    ", imageURL='" + imageURL + '\'' +
                    '}';
        }
    }
}
