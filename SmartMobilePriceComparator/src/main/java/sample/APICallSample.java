//package sample;
//
//import clients.GsonClient;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.Serializable;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//
//public class APICallSample {
//    private static final String USER_AGENT = "Mozilla/5.0";
//
//    public static void main(String[] args) throws Exception {
//        String response = sendGet("https://www.tatacliq.com/marketplacewebservices/v2/mpl/products/searchProducts/?searchText=%3Arelevance%3Acategory%3AMSH1210100%3AinStockFlag%3Atrue&isKeywordRedirect=false&isKeywordRedirectEnabled=true&channel=WEB&isTextSearch=false&isFilter=false&page=2&isPwa=true&pageSize=40&typeID=all");
//        ResponseParser responseParser = GsonClient.getClient().fromJson(response, ResponseParser.class);
//        List<SearchEntry> searchEntries = responseParser.searchresult;
//        for (SearchEntry entry : searchEntries) {
//            System.out.println(entry.toString());
//        }
//    }
//
//    // HTTP GET request
//    private static String sendGet(final String url) throws Exception {
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        // optional default is GET
//        con.setRequestMethod("GET");
//
//        //add request header
//        con.setRequestProperty("User-Agent", USER_AGENT);
//
//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//        return response.toString();
//    }
//
//    static class ResponseParser implements Serializable {
//        List<SearchEntry> searchresult;
//    }
//
//    static class SearchEntry implements Serializable {
//        String productname;
//        String webURL;
//        ProductPrice price;
//        String imageURL;
//        String brandname;
//        boolean cumulativeStockLevel;
//        String productCategoryType;
//
//        @Override
//        public String toString() {
//            return "SearchEntry{" +
//                    "productname='" + productname + '\'' +
//                    ", webURL='" + webURL + '\'' +
//                    ", price=" + price +
//                    ", imageURL='" + imageURL + '\'' +
//                    ", brandname='" + brandname + '\'' +
//                    ", cumulativeStockLevel=" + cumulativeStockLevel +
//                    ", productCategoryType='" + productCategoryType + '\'' +
//                    '}';
//        }
//    }
//
//    static class ProductPrice implements Serializable {
//        PriceDetails mrpPrice;
//        PriceDetails sellingPrice;
//
//        @Override
//        public String toString() {
//            return "ProductPrice{" +
//                    "mrpPrice=" + mrpPrice +
//                    ", sellingPrice=" + sellingPrice +
//                    '}';
//        }
//    }
//
//    static class PriceDetails implements Serializable {
//        String currencyIso;
//        String doubleValue;
//        String formattedValueNoDecimal;
//        String formattedValue;
//
//        @Override
//        public String toString() {
//            return "PriceDetails{" +
//                    "currencyIso='" + currencyIso + '\'' +
//                    ", doubleValue='" + doubleValue + '\'' +
//                    ", formattedValueNoDecimal='" + formattedValueNoDecimal + '\'' +
//                    ", formattedValue='" + formattedValue + '\'' +
//                    '}';
//        }
//    }
//}
