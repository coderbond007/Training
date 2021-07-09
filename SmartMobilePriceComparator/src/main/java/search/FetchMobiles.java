package search;

import bean.MobileResponseBean;
import bean.SearchResults;
import clients.DBClient;
import clients.PrintStreamClient;
import org.jsoup.helper.StringUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import static constants.Constants.ADD_SYMBOL;
import static constants.Constants.COMMA_SYMBOL;
import static constants.Constants.EMPTY_STRING;
import static constants.Constants.PERCENTAGE_SYMBOL;
import static constants.Constants.SPACE_SYMBOL;

public class FetchMobiles {
    private static final String SEARCH_ALL_MOBILES_PROC = "CALL SEARCH_ALL_MOBILES(?);";
    private static final String GET_DATA_QUERY = "CALL FETCH_MOBILE_DATA(?);";
    public static final String FETCH_MOBILE_DETAILS_PROC_CALL = "CALL FETCH_MOBILE_DETAILS(?);";
    private static final int MOBILE_NAME_COMPRESSED_NOW_INDEX = 1;

    private static final String MOBILE_ID_PARAM = "ID";
    private static final String MOBILE_NAME_PARAM = "MOBILE_NAME";
    private static final String RAM_PARAM = "RAM";
    private static final String COLOR_PARAM = "COLOR";
    private static final String PRICE_PARAM = "PRICE";
    private static final String IMAGE_URL_PARAM = "IMAGE_URL";

    private static final String ACTUAL_MOBILE_NAME_PARAM = "ACTUAL_MOBILE_NAME";
    private static final String ROM_PARAM = "rom";
    private static final String INTERNAL_STORAGE = "Internal Storage";
    private static final String AVAILABLE_STATUS_PARAM = "AVAILABLE_STATUS";
    private static final String MOBILE_LINK_PARAM = "MOBILE_LINK";

    private static final String NO_IMAGE_URL = "../image/noMobile.png";

    @Deprecated
    public static SearchResults fetchAllMobiles(final String searchQuery) throws SQLException, IOException {
        final long startTime = System.currentTimeMillis();
        SearchResults searchResults = new SearchResults();
        PreparedStatement preparedStatement = createPreparedStatement(searchQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        insertDataInSearchBean(resultSet, searchResults);
        final long endTime = System.currentTimeMillis();
        System.out.println("Time Taken for fetchAllMobiles : " + (endTime - startTime) + "ms");
        return searchResults;
    }

    @Deprecated
    public static SearchResults fetchAllMobiles_NEW(final String searchQuery) throws IOException, SQLException {
        final long startTime = System.currentTimeMillis();
        SearchResults searchResults = new SearchResults();
        try {
            List<Long> ids = FastSearchFuzzySearch.getResult(searchQuery);
            for (final Long currentID : ids) {
                PreparedStatement preparedStatement = DBClient.getPreparedStatement(GET_DATA_QUERY);
                preparedStatement.setLong(1, currentID);
                ResultSet resultSet = preparedStatement.executeQuery();
                insertDataInSearchBean(resultSet, searchResults);
            }
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Time Taken for fetchAllMobiles : " + (endTime - startTime) + "ms");
        return searchResults;
    }

    public static List<MobileResponseBean> fetchAllMobiles_Version2(final String searchQuery) throws IOException, SQLException {
        final long startTime = System.currentTimeMillis();
        List<MobileResponseBean> responseBeans = new ArrayList<>();
        try {
//            Long[] l = new Long[]{4777L,
//                    5020L,
//                    4665L,
//                    4601L,
//                    3289L,
//                    4002L,
//                    2547L, 3764L, 2549L, 2558L, 2562L, 2563L, 2566L, 2567L, 2577L, 2580L, 2584L, 2588L, 2590L, 2592L, 2594L, 2598L, 2599L};
            // = FastSearchFuzzySearch.getResult(searchQuery);
            List<Long> ids = FastSearchFuzzySearch.getResult(searchQuery);
            for (final Long currentID : ids) {
                try {
                    PreparedStatement preparedStatement = DBClient.getPreparedStatement(FETCH_MOBILE_DETAILS_PROC_CALL);
                    preparedStatement.setLong(1, currentID);
                    MobileResponseBean mobileResponseBean = getResponseBean(preparedStatement);
                    responseBeans.add(mobileResponseBean);
                } catch (Exception e) {
                    e.printStackTrace(PrintStreamClient.getClient());
                }
            }
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Time Taken for fetchAllMobiles : " + (endTime - startTime) + "ms");
        return responseBeans;
    }

    public static MobileResponseBean getResponseBean(final PreparedStatement preparedStatement) throws SQLException {
        MobileResponseBean mobileResponseBean = new MobileResponseBean();
        try {
            boolean hasResults = preparedStatement.execute();
            int counter = 0;
            long minPrice = Long.MAX_VALUE;
            long completeMinPrice = Long.MAX_VALUE;
            while (hasResults) {
                ResultSet resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    try {
                        if (counter == 0) {
                            String name = resultSet.getString(ACTUAL_MOBILE_NAME_PARAM);
                            String ram = resultSet.getString(RAM_PARAM);
                            String rom = resultSet.getString(ROM_PARAM);
                            String imageURL = resultSet.getString(IMAGE_URL_PARAM);

                            mobileResponseBean.setName(formatName(name));

                            Map<String, String> specs = new LinkedHashMap<>();
                            specs.put(RAM_PARAM, ram);
                            specs.put(INTERNAL_STORAGE, rom);
                            mobileResponseBean.setSpecs(specs);
                            if (StringUtil.isBlank(imageURL))
                                imageURL = NO_IMAGE_URL;
                            mobileResponseBean.setImageURL(imageURL);
                        } else if (counter == 1) {
                            Long price = resultSet.getLong(PRICE_PARAM);
                            Integer availStat = resultSet.getInt(AVAILABLE_STATUS_PARAM);
                            String mobileLink = resultSet.getString(MOBILE_LINK_PARAM);
                            if (availStat == 1)
                                minPrice = Math.min(minPrice, price);
                            completeMinPrice = Math.min(completeMinPrice, price);
                            mobileResponseBean.addStoreDetails(
                                    formatPrice(price),
                                    formatAvailabilityStatus(availStat),
                                    mobileLink
                            );
                        } else if (counter == 2) {
                            String screenSize = resultSet.getString("screensize");
                            String camera = resultSet.getString("camera");
                            String battery = resultSet.getString("battery");
                            Map<String, String> specs = mobileResponseBean.getSpecs();
                            if (specs == null) specs = new HashMap<>();
                            if (!StringUtil.isBlank(screenSize)) specs.put("Screen-Size", screenSize.trim());
                            if (!StringUtil.isBlank(camera)) specs.put("Camera", camera.trim());
                            if (!StringUtil.isBlank(battery)) specs.put("Battery", battery.trim());
                            mobileResponseBean.setSpecs(specs);
                        } else throw new RuntimeException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ++counter;
                hasResults = preparedStatement.getMoreResults();
            }
            if (minPrice != Long.MAX_VALUE) mobileResponseBean.setMinPrice(formatPrice(minPrice));
            else mobileResponseBean.setMinPrice(formatPrice(completeMinPrice));
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return mobileResponseBean;
    }

    private static String formatName(final String name) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] parts = name.split(SPACE_SYMBOL);
        boolean first = true;
        for (String part : parts) {
            if (!first) stringBuilder.append(SPACE_SYMBOL);
            first = false;
            String rem = part.substring(1);
            stringBuilder.append(Character.toUpperCase(part.charAt(0)));
            stringBuilder.append(rem);
        }
        return stringBuilder.toString();
    }

    private static String formatAvailabilityStatus(final Integer availStat) {
        return availStat == 0 ? "Oops! Out of Stock" : "Available";
    }

    private static String formatPrice(Long price) {
        char[] revPrice = new StringBuilder(Long.toString(price)).reverse().toString().toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        boolean firstComma = true;
        boolean putComma = false;
        int counter = 0;
        for (char c : revPrice) {
            if (putComma) {
                stringBuilder.append(COMMA_SYMBOL);
                putComma = false;
            }
            stringBuilder.append(c);
            ++counter;
            if (firstComma) {
                if (counter == 3) {
                    putComma = true;
                    counter = 0;
                    firstComma = false;
                }
            } else {
                if (counter == 2) {
                    putComma = true;
                    counter = 0;
                }
            }
        }
        return "Rs." + stringBuilder.reverse().toString();
    }

    private static void insertDataInSearchBean(final ResultSet resultSet, final SearchResults searchResults) throws SQLException {
        Set<Long> idsUsed = new HashSet<>();
        while (resultSet.next()) {
            Long id = null;
            String mobileName = null;
            String ram = null;
            String color = null;
            Long price = null;
            String imageURL = null;
            int available_status = 1;
            try {
                id = resultSet.getLong(MOBILE_ID_PARAM);
            } catch (Exception ignored) {

            }
            try {
                mobileName = resultSet.getString(MOBILE_NAME_PARAM);
            } catch (Exception ignored) {

            }

            try {
                ram = resultSet.getString(RAM_PARAM);
            } catch (Exception ignored) {

            }
            try {
                color = resultSet.getString(COLOR_PARAM);
            } catch (Exception ignored) {

            }
            try {
                price = resultSet.getLong(PRICE_PARAM);
            } catch (Exception ignored) {

            }
            try {
                imageURL = resultSet.getString(IMAGE_URL_PARAM);
            } catch (Exception ignored) {

            }
            if (idsUsed.contains(id)) continue;
            ram = processRam(ram);
            idsUsed.add(id);
            searchResults.addMobileDetails(mobileName, price, id, ram, color, imageURL);
        }
        resultSet.close();
    }

    private static PreparedStatement createPreparedStatement(final String searchQuery) throws SQLException, IOException {
        final String[] keywords = searchQuery.split(SPACE_SYMBOL);
        StringJoiner stringJoiner = new StringJoiner(PERCENTAGE_SYMBOL);
        for (final String keyword : keywords) {
            for (char part : keyword.toCharArray()) {
                stringJoiner.add("" + part);
            }
        }
        final String param = PERCENTAGE_SYMBOL + stringJoiner.toString() + PERCENTAGE_SYMBOL;
        PreparedStatement preparedStatement = DBClient.getPreparedStatement(SEARCH_ALL_MOBILES_PROC);
        preparedStatement.setString(MOBILE_NAME_COMPRESSED_NOW_INDEX, param);
        return preparedStatement;
    }

    public static String processRam(final String ram) {
        if (org.eclipse.jetty.util.StringUtil.isBlank(ram))
            return null;
        List<String> list = splitter(ram);
        String[] parts = new String[2];
        parts[0] = list.get(0);
        parts[1] = list.get(1);

        String ramNow = parts[0];
        String romNow = parts[1];
        if (ramNow.equals("0")) {
            ramNow = EMPTY_STRING;
        }
        if (romNow.equals("0")) {
            romNow = EMPTY_STRING;
        }
        if (ramNow.length() >= 3) {
            ramNow += "MB";
        } else if (ramNow.length() >= 1) {
            ramNow += "GB";
        }
        if (romNow.length() > 0) {
            romNow += "GB";
        }
        String ans;
        if (!romNow.equals(EMPTY_STRING) && !ramNow.equals(EMPTY_STRING)) {
            ans = ramNow + ADD_SYMBOL + romNow;
        } else {
            ans = ramNow + romNow;
        }
        return ans;
    }

    private static final List<String> splitter(final String s) {
        int ind = s.indexOf(ADD_SYMBOL);
        List<String> ans = new ArrayList<>();
        if (ind >= 0)
            ans.add(s.substring(0, ind));
        if (ind < s.length())
            ans.add(s.substring(ind + 1));
        while (ans.size() < 2) ans.add(EMPTY_STRING);
        return ans;
    }
}