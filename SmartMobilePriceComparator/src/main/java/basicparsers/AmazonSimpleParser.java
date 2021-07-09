package basicparsers;

import clients.PrintStreamClient;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import util.Util;

import java.io.FileNotFoundException;

import static parsers.AmazonParserModified.COMPARE_TABLE_ID;
import static parsers.AmazonParserModified.PRICE_SELECTOR_FIRST;
import static parsers.AmazonParserModified.PRICE_SELECTOR_SECOND;

public class AmazonSimpleParser extends AbstractSimpleParser {
    @Override
    public Long getPrice(Document document) {
        String price = null;
        try {
            Element comparisonTable = document.getElementById(COMPARE_TABLE_ID);
            String price1 = comparisonTable.select(PRICE_SELECTOR_FIRST).text();
            String price2 = comparisonTable.select(PRICE_SELECTOR_SECOND).text();
            price = !StringUtil.isBlank(price1) ? price1 : price2;
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        if (StringUtil.isBlank(price)) {
            try {
                Element element = document.getElementById("priceblock_ourprice");
                if (element == null) element = document.getElementById("priceblock_dealprice");
                String priceString = element.text();
                price = Util.extractPrice(priceString);
            } catch (Exception e) {
                try {
                    e.printStackTrace(PrintStreamClient.getClient());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return price == null ? null : Long.parseLong(Util.extractPrice(price));
    }

    @Override
    public boolean getAvailabilityStatus(final Document document) {
        boolean status = true;
        try {
            String data =
                    document
                            .getElementById("availability")
                            .text()
                            .toLowerCase();
            if (data.contains("Currently unavilable".toLowerCase())) {
                status = false;
            }
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return status;
    }
}
