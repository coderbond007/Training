package parsers;

import Bean.SiteDataParsed;
import clients.GsonClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlipkartParser implements Parser {
    @Override
    public SiteDataParsed parseData(String pageSource, String link) {
        Document doc = Jsoup.parse(pageSource);
        Map<String, String> featuresMap = getFeatures(doc);
        featuresMap.put("LINK", link);
        String featuresJSON =
                GsonClient
                        .getClient()
                        .toJson(featuresMap);
        List<String> allLinks = getAllLinks(doc);
        SiteDataParsed siteDataParsed = new SiteDataParsed();
        siteDataParsed.setJson(featuresJSON);
        siteDataParsed.setLinks(allLinks);
        return siteDataParsed;
    }

    @Override
    public Map<String, String> getFeatures(Document document) {
        Element allTables = document.getElementsByClass("_3Rrcbo").first();
        Elements tables = allTables.getElementsByClass("_2RngUh");
        Map<String, String> features = new HashMap<>();
        for (Element currentTable : tables) {
            Elements allRows = currentTable.select("tr._3_6Uyw.row");
            for (Element row : allRows) {
                String key = row.select("td._3-wDH3.col.col-3-12").first().text();
                String value = row.getElementsByClass("_3YhLQA").first().text();
                features.put(key.toLowerCase(), value.toLowerCase());
            }
        }
        return features;
    }

    @Override
    public List<String> getAllLinks(Document document) {
        List<String> allLinks = new ArrayList<>();
        Elements links = document.select("a[href]");
        for (Element link : links) {
            allLinks.add(link.attr("abs:href"));
        }
        return allLinks;
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }
}
