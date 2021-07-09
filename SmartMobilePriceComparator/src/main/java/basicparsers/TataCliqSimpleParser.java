package basicparsers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Util;

public class TataCliqSimpleParser extends AbstractSimpleParser {


    @Override
    public Long getPrice(Document document) {
        Element element =
                document
                        .getElementsByClass("_3BuuEa4DZJe-0OCEQmi_K_")
                        .first();
        String price = element.text();
        price = Util.extractPrice(price);
        return price == null ? null : Long.parseLong(price);
    }

    @Override
    public boolean getAvailabilityStatus(Document document) {
        Elements elements = document.select("div._1FUeUEk-zA1RSFwfF5Ss2Q._3ohVlBQVxzL7rqc66lghTo");
        if (elements.size() == 0)
            return true;
        else
            return false;
    }
}