package basicparsers;

import clients.PrintStreamClient;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import util.Util;

import java.io.FileNotFoundException;

import static constants.Constants.ZERO;

public class FlipkartSimpleParser extends AbstractSimpleParser {
    @Override
    public Long getPrice(Document document) {
        Long price = null;
        try {
            String priceString = document.select("div._1vC4OE._3qQ9m1").text();
            price = Long.parseLong(Util.extractPrice(priceString));
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return price;
    }

    @Override
    public boolean getAvailabilityStatus(Document document) {
        Elements elements = null;
        try {
            elements = document.getElementsByClass("_9-sL7L");
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return elements == null || elements.size() == ZERO;
    }
}
