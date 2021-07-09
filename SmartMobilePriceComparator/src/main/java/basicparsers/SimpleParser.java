package basicparsers;

import org.jsoup.nodes.Document;

public interface SimpleParser {
    void parseData(String pageSource);

    Long getPrice(final Document document);

    boolean getAvailabilityStatus(final Document document);
}