package flipkart;

import clients.CrawlClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static constants.Constants.EMPTY_STRING;

public class FetchLinks {
    public static final String QUERY = "https://www.flipkart.com/search?sid=tyy%2C4io&otracker=CLP_Filters&p%5B%5D=facets.availability%255B%255D%3DExclude%2BOut%2Bof%2BStock&p%5B%5D=facets.serviceability%5B%5D%3Dfalse&p%5B%5D=facets.price_range.from%3D7000&p%5B%5D=facets.price_range.to%3DMax&page=($value$)";
    public static final String VALUE = "($value$)";

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("/Users/pradyumn.ag/IdeaProjects/SmartMobilePriceComparator/src/main/java/flipkart/links.txt")));
        for (int it = 0; it < 50; ++it) {
            try {
                List<String> nwoLinks = fetchLinks(it);
                for (String link : nwoLinks) {
                    out.println(link);
                }
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        out.close();
    }

    private static List<String> fetchLinks(int pageNumber) throws Exception {
        String URL = QUERY.replace(VALUE, EMPTY_STRING + pageNumber);
        List<String> links = new ArrayList<>();
        try {
            String pageSource = CrawlClient.getCrawlClient().crawl(URL).getContent();
            Document document = Jsoup.parse(pageSource);
            Elements elements = document.getElementsByClass("_31qSD5");

            for (Element element : elements) {
                try {
                    Elements nowLinks = element.select("a[href]");
                    for (Element nowLink : nowLinks) {
                        links.add(nowLink.attr("href"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return links;
    }
}
