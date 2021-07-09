package basicparsers;

import clients.PrintStreamClient;
import org.jsoup.nodes.Document;
import util.Util;

import java.io.FileNotFoundException;

public class SnapDealSimpleParser extends AbstractSimpleParser {
    @Override
    public Long getPrice(Document document) {
        String priceString = Util
                .extractPrice(
                        document
                                .getElementsByClass("payBlkBig")
                                .first()
                                .text()
                );
        return priceString == null ? null : Long.parseLong(priceString);
    }

    @Override
    public boolean getAvailabilityStatus(Document document) {
        boolean isAvailable = false;
        try {
            isAvailable = document.getElementsByClass("sold-out-err").size() == 0;
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return isAvailable;
    }
}
