package sample;

import clients.GsonClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class SampleAmazonParser {
    public static final String PATH1 = "/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/data/output1.html";
    public static final String PATH2 = "/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/data/output2.html";
    public static final String PATH3 = "/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/data/output3.html";
    public static final String PATH4 = "/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/data/output4.html";
    public static final String PATH = PATH4;

    public static void main(String[] args) throws IOException {
        String page = fetchSource();
        validate(page);
    }

    public static int validate(final String pageSource) throws FileNotFoundException {
        int check = 0;
        Document document = Jsoup.parse(pageSource);
        StringBuilder stringBuilder = new StringBuilder();
        MobileData mobileData = null;
        try {
            mobileData = fetchDataUsingLogic1(document);
        } catch (Exception e) {
            ++check;
            stringBuilder.append("Failed by first\n");
        }
        try {
            mobileData = fetchDataUsingLogic2(document);
        } catch (Exception e) {
            check += 2;
            stringBuilder.append("Failed by second\n");
        }
        if (check == 3) {
            System.out.println(stringBuilder.toString());
        } else {
            PrintWriter out = new PrintWriter(new FileOutputStream(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/sample/data.json"), true));
            out.println(GsonClient.getClient().toJson(mobileData));
            out.close();
        }
        return check;
    }

    /**
     * for old one
     *
     * @param document
     * @return
     * @throws IOException
     */
    public static final MobileData fetchDataUsingLogic1(final Document document) throws IOException {
        MobileData mobileData = new MobileData();

        Map<String, String> map = new LinkedHashMap<>();
        Element comparisonTable = document.getElementById("HLCXComparisonTable");
        String thisItem = comparisonTable.select("#comparison_title > span.a-size-base.a-color-base.a-text-bold").text();
        if (!thisItem.equalsIgnoreCase("This item")) {
            throw new RuntimeException();
        }

        String title = document.getElementById("productTitle").text();
        String checkTitle = comparisonTable.select("#comparison_title > span:nth-child(2)").text();
        String price = comparisonTable.select("#comparison_price_row > td.comparison_baseitem_column > span").text();
        if (!checkTitle.equalsIgnoreCase(title)) {
            throw new RuntimeException();
        }
//        System.out.println(title + " " + checkTitle + " " + price);
        mobileData.price = price;
        mobileData.title = title;

        Elements attributes = comparisonTable.getElementsByClass("comparison_other_attribute_row");
        for (Element attribute : attributes) {
            String propertyName = attribute.select("th > span").text();
            String propertyValue = attribute.select("td.comparison_baseitem_column > span").text();
//            System.out.println(propertyName + "===========" + propertyValue);
            map.put(propertyName, propertyValue);
        }
        mobileData.map = map;
        return mobileData;
    }

    /**
     * for new one
     *
     * @param document
     * @return
     */
    public static final MobileData fetchDataUsingLogic2(final Document document) {
        Element comparisonTable = document.getElementById("HLCXComparisonTable");
//        System.out.println(document.outerHtml());
//        System.out.println(comparisonTable.html());
//        String youAreViewing = comparisonTable.select("tbody > tr.comparison_table_image_row.border-none > th:nth-child(2) > div:nth-child(2) > div > span > strong").text();
        String youAreViewing1 = document.select("#HLCXComparisonTable > tbody > tr.comparison_table_image_row.border-none > th:nth-child(2) > div:nth-child(2) > div > span > strong").text();
//        System.out.println(youAreViewing);
        if (!youAreViewing1.equalsIgnoreCase("You are viewing:")) {
            throw new RuntimeException();
        }

        MobileData mobileData = new MobileData();
        String title = document.getElementById("productTitle").text();
        String mobileTitleData = comparisonTable.select("tbody > tr.comparison_table_image_row.border-none > th:nth-child(2) > div:nth-child(2) > div > span").text();
//        System.out.println(title + " == " + mobileTitleData);
        if (!mobileTitleData.toLowerCase().contains(title.toLowerCase())) {
            throw new RuntimeException();
        }
        String price = comparisonTable.select("tbody > tr:nth-child(2) > td:nth-child(2) > span").text();

//        System.out.println(price);
//        String key1 = comparisonTable.select("tbody > tr:nth-child(6) > th > div > div.attribute-heading-icon-label-wrapper > div.attribute-heading-label > span").text();
//        String value1 = comparisonTable.select("tbody > tr:nth-child(6) > td.base-item-column").text();
//        String key2 = comparisonTable.select("tbody > tr:nth-child(7) > th > div > div.attribute-heading-icon-label-wrapper > div.attribute-heading-label > span").text();
//        String value2 = comparisonTable.select("tbody > tr:nth-child(7) > td.base-item-column").text();
//        System.out.println(key1 + " " + value1);
//        System.out.println(key2 + " " + value2);

        Map<String, String> map = new LinkedHashMap<>();
        final String accessKey = "tbody > tr:nth-child($value$) > th > div > div.attribute-heading-icon-label-wrapper > div.attribute-heading-label > span";
        final String accessValue = "tbody > tr:nth-child($value$) > td.base-item-column";
        for (int iter = 0; iter < 100; ++iter) {
            try {
                String key = comparisonTable.select(accessKey.replace("$value$", "" + iter)).text().trim();
                String value = comparisonTable.select(accessValue.replace("$value$", "" + iter)).text().trim();
//                System.out.println(key + " " + value);
                map.put(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mobileData.title = title;
        mobileData.price = price;
        mobileData.map = map;
        return mobileData;
    }

    private static String fetchSource() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(PATH)));
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            stringBuilder.append(bufferedReader.readLine());
        }
        return stringBuilder.toString();
    }

    static class MobileData implements Serializable {
        Map<String, String> map;
        String price;
        String title;

        @Override
        public String toString() {
            return "MobileData{" +
                    "map=" + map +
                    ", price='" + price + '\'' +
                    '}';
        }
    }
}
