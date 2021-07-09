package parsers;

import Bean.SiteDataParsed;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

public interface Parser {
    SiteDataParsed parseData(String pageSource, String link);

    Map<String, String> getFeatures(Document document);

    List<String> getAllLinks(Document document);
}
