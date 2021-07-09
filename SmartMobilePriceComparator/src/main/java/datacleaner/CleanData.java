package datacleaner;

import clients.DBClient;
import clients.GsonClient;
import server.BuildEnvironment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CleanData {
    public static void main(String[] args) throws IOException, SQLException {
        updateDataFromDB();
    }

    public static final void updateDataFromDB() throws IOException, SQLException {
        BuildEnvironment.build();
        PreparedStatement preparedStatement = DBClient.getPreparedStatement("SELECT actual_mobile_name, ram, color, brand FROM MOBILE_DETAILS;");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<MyData> list = new ArrayList<>();
        while (resultSet.next()) {
            String actual_mobile_name = resultSet.getString("actual_mobile_name");
            String variant = resultSet.getString("ram");
            String color = resultSet.getString("color");
            String brand = resultSet.getString("brand");
            MyData myData = new MyData(actual_mobile_name, variant, color, brand);
            list.add(myData);
        }

        PrintWriter out = new PrintWriter(new FileOutputStream(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/data/entireData.json")));
        out.println(GsonClient.getClient().toJson(list));
        out.close();
    }

    static class MyData implements Serializable {
        String actual_mobile_name;
        String variant;
        String color;
        String brand;

        public MyData(String actual_mobile_name, String variant, String color, String brand) {
            this.actual_mobile_name = actual_mobile_name;
            this.variant = variant;
            this.color = color;
            this.brand = brand;
        }
    }
}
