package search;

import bean.MobileDetailsResult;
import clients.DBClient;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static search.FetchMobiles.processRam;

public class FetchMobileWithID {
    private static final String FETCH_MOBILE_DATA = "CALL FETCH_MOBILE_DATA(?);";
    private static final int MOBILE_ID_NOW_INDEX = 1;
    private static final String MOBILE_ID_PARAM = "MOBILE_ID";
    private static final String MOBILE_NAME_PARAM = "MOBILE_NAME";
    private static final String RAM_PARAM = "RAM";
    private static final String COLOR_PARAM = "COLOR";
    private static final String PRICE_PARAM = "PRICE";
    private static final String LINK_PARAM = "LINK";
    private static final String SELLER_NAME_PARAM = "SELLER_NAME";
    private static final String IMAGE_URL_PARAM = "IMAGE_URL";
    private static final String AVAILABLE_STATUS_PARAM = "AVAILABLE_STATUS";

    public static MobileDetailsResult fetchAllMobiles(final long searchQuery) throws SQLException, IOException {
        PreparedStatement preparedStatement = createPreparedStatement(searchQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        MobileDetailsResult mobileDetailsResult = getDetails(resultSet);
        return mobileDetailsResult;
    }

    public static MobileDetailsResult fetchDetailsForExtension(final List<Long> allIDs) throws SQLException, IOException {
        MobileDetailsResult mobileDetailsResult = new MobileDetailsResult();
        for (final Long currentID : allIDs) {
            PreparedStatement preparedStatement = createPreparedStatement(currentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            mobileDetailsResult = getDetails(resultSet, mobileDetailsResult);
        }
        return mobileDetailsResult;
    }

    private static PreparedStatement createPreparedStatement(final long searchIndex) throws SQLException, IOException {
        PreparedStatement preparedStatement = DBClient.getPreparedStatement(FETCH_MOBILE_DATA);
        preparedStatement.setLong(MOBILE_ID_NOW_INDEX, searchIndex);
        return preparedStatement;
    }

    private static MobileDetailsResult getDetails(ResultSet resultSet) throws SQLException {
        MobileDetailsResult mobileDetailsResult = new MobileDetailsResult();
        while (resultSet.next()) {
            String ram = null;
            String color = null;
            long price = 0;
            String link = null;
            String seller_name = null;
            String name = null;
            String imageURL = null;
            String avail = null;

            try {
                name = resultSet.getString(MOBILE_NAME_PARAM);
            } catch (Exception ignored) {

            }
            try {
                ram = resultSet.getString(RAM_PARAM);
            } catch (Exception ignored) {

            }
            try {
                price = resultSet.getLong(PRICE_PARAM);
            } catch (Exception ignored) {

            }
            try {
                link = resultSet.getString(LINK_PARAM);
            } catch (Exception ignored) {

            }
            try {
                seller_name = resultSet.getString(SELLER_NAME_PARAM);
            } catch (Exception ignored) {

            }
            try {
                color = resultSet.getString(COLOR_PARAM);
            } catch (Exception ignored) {

            }
            try {
                imageURL = resultSet.getString(IMAGE_URL_PARAM);
            } catch (Exception e) {

            }
            try {
                avail = resultSet.getInt(AVAILABLE_STATUS_PARAM) == 0 ? "Oops! Out of Stock" : "Available";
            } catch (Exception e) {

            }
            ram = processRam(ram);
            mobileDetailsResult.addMobileDetails(price, name, link, seller_name, color, ram, imageURL, avail);
        }

        return mobileDetailsResult;
    }

    private static MobileDetailsResult getDetails(ResultSet resultSet, MobileDetailsResult mobileDetailsResult) throws SQLException {
        while (resultSet.next()) {
            String ram = null;
            String color = null;
            long price = 0;
            String link = null;
            String seller_name = null;
            String name = null;
            String imageURL = null;
            String avail = null;

            try {
                name = resultSet.getString(MOBILE_NAME_PARAM);
            } catch (Exception ignored) {

            }
            try {
                ram = resultSet.getString(RAM_PARAM);
            } catch (Exception ignored) {

            }
            try {
                price = resultSet.getLong(PRICE_PARAM);
            } catch (Exception ignored) {

            }
            try {
                link = resultSet.getString(LINK_PARAM);
            } catch (Exception ignored) {

            }
            try {
                seller_name = resultSet.getString(SELLER_NAME_PARAM);
            } catch (Exception ignored) {

            }
            try {
                color = resultSet.getString(COLOR_PARAM);
            } catch (Exception ignored) {

            }
            try {
                imageURL = resultSet.getString(IMAGE_URL_PARAM);
            } catch (Exception e) {

            }
            try {
                avail = resultSet.getInt(AVAILABLE_STATUS_PARAM) == 0 ? "Oops! Out of Stock" : "Available";
            } catch (Exception e) {

            }
            mobileDetailsResult.addMobileDetails(price, name, link, seller_name, color, ram, imageURL, avail);
        }
        return mobileDetailsResult;
    }
}
