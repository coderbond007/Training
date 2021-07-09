package server;

import bean.BasicMobileDetails;
import clients.DBClient;
import crons.PriceUpdater;
import crons.TrendingCron;
import util.AppConfigAccessor;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildEnvironment {
    public static List<BasicMobileDetails> mobiles = null;

    public static void build() throws IOException, SQLException {
        AppConfigAccessor.initialize();
        DBClient.initiateConnection();
    }

    public static void runCRONS() {
        PriceUpdater.startScheduledTask();
        TrendingCron.startScheduledTask();
    }

    public static void loadListForFuzzySearch() throws IOException, SQLException {
        build();
        if (mobiles == null) mobiles = new ArrayList<>();
        else mobiles.clear();
        PreparedStatement preparedStatement = DBClient.getPreparedStatement("SELECT id, actual_mobile_name FROM MOBILE_DETAILS;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("actual_mobile_name");
//            String brand = resultSet.getString("brand");
//            String color = resultSet.getString("color");
            BasicMobileDetails basicMobileDetails = new BasicMobileDetails(name, id, null, null);
            mobiles.add(basicMobileDetails);
        }
        resultSet.close();
    }

    public static List<BasicMobileDetails> getMobiles() throws IOException, SQLException {
        final long startTime = System.currentTimeMillis();
        if (mobiles == null || mobiles.size() == 0) loadListForFuzzySearch();
        final long endTime = System.currentTimeMillis();
        System.out.println("time taken : " + (endTime - startTime) + "ms");
        return mobiles;
    }
}
