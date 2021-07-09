package search;

import clients.DBClient;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import me.xdrop.fuzzywuzzy.FuzzySearch;

public class FetchMobileWithCompressedName {
    private static String index = "id";
    private static String ram = "ram";
    private static String rom = "rom";
    private static String queryRam;
    private static String getQueryRom;
    private static final String FETCH_RamAndARom = "CALL FETCH_MOBILE_RamAndRom(?);";
    private static final int compressed_name_index = 1;

    public static List<Long> fetchResult(FastSearchFuzzySearch.ExtensionData ext) throws SQLException, IOException {
        queryRam = ext.ram;
        getQueryRom = ext.rom;
//        System.out.println(ext.compressed_name + "fd");
        return execute(ext.compressed_name);
    }

    public static void main(String[] args) throws IOException, SQLException {
        FastSearchFuzzySearch.ExtensionData extensionData = new FastSearchFuzzySearch.ExtensionData("6sappleiphone", "0", "64");
    }

    private static List<Long> execute(String query) throws IOException, SQLException {
        List<results> result = new ArrayList<>();
//        System.out.println(query);
        PreparedStatement preparedStatement = createPreparedStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            results re = new results();
            re.id = resultSet.getLong(index);
            re.ram = resultSet.getString(ram);
            re.rom = resultSet.getString(rom);
//            System.out.println(re.id + " " + re.ram + " " + re.rom);
            result.add(re);
        }
        ArrayList<Long> myres = new ArrayList<>();
        for (results r : result) {
            myres.add(r.id);
        }
        return myres;
    }

    private static PreparedStatement createPreparedStatement(final String compressed_name) throws SQLException, IOException {
//        System.out.println(FETCH_MOBILE_DATA + " " + searchIndex);
        PreparedStatement preparedStatement = DBClient.getPreparedStatement(FETCH_RamAndARom);
        preparedStatement.setString(compressed_name_index, compressed_name);
//        System.out.println(preparedStatement.getParameterMetaData());
        return preparedStatement;
    }

    static class results implements Comparable<results> {
        String ram;
        String rom;
        Long id;

        public int compareTo(results o) {
            int cnt1 = 0;
            int cnt2 = 0;
            if (this.ram.equals(queryRam) || this.ram.equals(getQueryRom))
                cnt1++;
            if (this.rom.equals(getQueryRom) || this.rom.equals(queryRam)) {
                cnt1++;
            }
            if (o.ram.equals(queryRam) || o.ram.equals(getQueryRom))
                cnt2++;
            if (o.rom.equals(getQueryRom) || o.rom.equals(queryRam)) {
                cnt2++;
            }
            return -cnt1 + cnt2;
        }
    }

}