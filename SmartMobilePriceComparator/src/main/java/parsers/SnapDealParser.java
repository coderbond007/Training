package parsers;

import clients.PrintStreamClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Util;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static constants.Constants.MI_REDMI;
import static constants.Constants.REDMI;
import static constants.Constants.SPACE_SYMBOL;
import static constants.Constants.XIAOMI_REDMI;

public class SnapDealParser extends AbstractParser {
    @Override
    public Map<String, String> getFeatures(Document document) {
        HashMap<String, String> map = new HashMap<>();
        Elements elements = document.getElementsByTag("tr");
        for (int i = 0; i < elements.size(); i++) {
            Elements row = elements.get(i).getElementsByTag("td");
            if (row.size() == 2) {
                String key = row.get(0).text().toLowerCase().trim();
                String val = row.get(1).text().toLowerCase().trim();
                map.put(key, val);
            }
        }
        Elements elements1 = document.getElementsByAttributeValue("itemprop", "title");
        Element element = elements1.get(elements1.size() - 1);
        String text = element.text().toLowerCase();
        if (text.equals("mobile phones")) {
            map.put("mobile", "true");
        }
        return map;
    }

    @Override
    public String getRam(Document document) {
        return Util.extractRam(map.get("ram"));
    }

    @Override
    public Integer getPrice(Document document) {
        return Integer.parseInt(Util.extractPrice(document.getElementsByClass("payBlkBig").first().text()));
    }

    @Override
    public String getInternalStorage(Document document) {
        return Util.extractRam(map.get("internal memory"));
    }

    @Override
    public String getColor(Document document) {
        return map.get("colour");
    }

    @Override
    public String getModelNumber(Document document) {
        return null;
    }

    @Override
    public String getModelName(Document document) {
        return document.getElementsByClass("pdp-e-i-head").text();
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

    @Override
    public String getImageUrl(Document document) {
        return document.getElementsByClass("cloudzoom").first().attr("src");
    }

    @Override
    public String getBrandName(Document document) {
        String bName = map.get("brand");
        bName = SPACE_SYMBOL + bName + SPACE_SYMBOL;
        bName = bName.toLowerCase().replace(XIAOMI_REDMI, REDMI).replace(MI_REDMI, REDMI);
        return bName.trim();
    }

    @Override
    public boolean isPhone() {
        return map.containsKey("mobile");
    }
}
