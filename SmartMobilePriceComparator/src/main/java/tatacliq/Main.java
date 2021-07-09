package tatacliq;

import clients.GsonClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) throws Exception {
        final String QUERY = "https://www.tatacliq.com/marketplacewebservices/v2/mpl/products/searchProducts/?searchText=%3Arelevance%3Acategory%3AMSH1210100%3AinStockFlag%3Atrue&isKeywordRedirect=false&isKeywordRedirectEnabled=true&channel=WEB&isTextSearch=false&isFilter=false&page=($value$)&isPwa=true&pageSize=40&typeID=all";
        final String QUERY1 = "https://www.tatacliq.com/marketplacewebservices/v2/mpl/products/searchProducts/?searchText=%3Arelevance%3Acategory%3AMSH1210102%3AinStockFlag%3Atrue&isKeywordRedirect=false&isKeywordRedirectEnabled=true&channel=WEB&isTextSearch=false&isFilter=false&page=($value$)&isPwa=true&pageSize=40&typeID=all";
        final String VALUE = "($value$)";
        ResponseParser allresults = new ResponseParser();
        allresults.searchresult = new ArrayList<>();

        for (int it = 1; it <= 30; ++it) {
            try {
                String jsonData = RequestHandler.sendGet(
                        QUERY1.replace(VALUE, it + "")
                );
                ResponseParser responseParser = GsonClient.getClient().fromJson(jsonData, ResponseParser.class);
                Thread.sleep(2000);
                if (responseParser.searchresult == null || responseParser.searchresult.size() == 0) continue;
                allresults.searchresult.addAll(responseParser.searchresult);
            } catch (Exception e) {
                System.err.println("Failed for page : " + it);
                e.printStackTrace();
            }
        }

        PrintWriter out = new PrintWriter(new FileOutputStream(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/tatacliq/iphonesearchresults.json")));
        out.println(GsonClient.getClient().toJson(allresults));
        out.flush();
        out.close();
    }
}
