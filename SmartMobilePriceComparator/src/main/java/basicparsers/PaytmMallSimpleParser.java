package basicparsers;

import clients.PrintStreamClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Util;

import java.io.FileNotFoundException;

public class PaytmMallSimpleParser extends AbstractSimpleParser {

    @Override
    public Long getPrice(Document document) {
        Element priceElement = document.getElementsByClass("_1V3w").first();
        String priceString = priceElement.text();
        Long price = null;
        try {
            price = Long
                    .parseLong(
                            Util.extractPrice(
                                    priceString
                            )
                    );
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
        try {
            Elements available = document.select("div._2CNI.UJ30");
            if (available.size() == 0) {
                return true;
            }
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}
