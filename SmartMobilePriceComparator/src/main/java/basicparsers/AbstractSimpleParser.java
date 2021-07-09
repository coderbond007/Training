package basicparsers;

import clients.PrintStreamClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileNotFoundException;


public abstract class AbstractSimpleParser implements SimpleParser {
    private Long priceUpdated;
    private boolean availabilityUpdated;

    @Override
    public void parseData(String pageSource) {
        try {
            Document document = Jsoup.parse(pageSource);
            priceUpdated = getPrice(document);
            availabilityUpdated = getAvailabilityStatus(document);
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            throw e;
        }
    }

    public Long getPriceUpdated() {
        return priceUpdated;
    }

    public boolean getAvailabilityUpdated() {
        return availabilityUpdated;
    }
}
