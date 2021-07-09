package parsers;

import clients.PrintStreamClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Util;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static constants.Constants.FIVE;
import static constants.Constants.MI_REDMI;
import static constants.Constants.REDMI;
import static constants.Constants.SPACE_SYMBOL;
import static constants.Constants.XIAOMI_REDMI;
import static constants.Constants.ZERO;

public class FlipkartParser extends AbstractParser {
    private static final String[] COMMON_FEATURES = {"Touchscreen", "Operating System", "Internal Storage", "RAM", "Expandable Storage", "Primary Camera Available", "Secondary Camera", "Smartphone"};
    private static final String FLIPKART_RAM = "ram";
    private static final String FLIPAKRT_INTERNAL_STORAGE = "internal storage";
    private static final String FLIPKART_PRICE = "price";
    private static final String FLIPKART_COLOR = "color";
    Document tempDoc;

    @Override
    public final Map<String, String> getFeatures(final Document document) {
        Map<String, String> features = new HashMap<>();
        try {
            Elements keywords = document.getElementsByClass("_1KHd47");
            for (int i = 0; i < keywords.size(); i++) {
                Element element = keywords.get(i);
                if (element.text().toLowerCase().equals("mobiles")) {
                    map.put("isphone", "true");
                }
            }
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        try {
            final Elements tables =
                    document
                            .getElementsByClass("_3Rrcbo")
                            .first()
                            .getElementsByClass("_2RngUh");
            if (tables == null || tables.size() == ZERO)
                return features;

            for (Element currentTable : tables) {
                final Elements allRows = currentTable.select("tr._3_6Uyw.row");
                if (allRows == null)
                    continue;
                for (Element row : allRows) {
                    try {
                        final String key = row
                                .select("td._3-wDH3.col.col-3-12")
                                .first()
                                .text();

                        final String value = row
                                .getElementsByClass("_3YhLQA")
                                .first()
                                .text();
                        features.put(key.toLowerCase(), value.toLowerCase());
                    } catch (Exception e) {
                        try {
                            e.printStackTrace(PrintStreamClient.getClient());
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return features;
    }

    @Override
    public final String getRam(final Document document) {
        String ram = null;
        try {
            ram = map.get(FLIPKART_RAM);
            ram = Util.extractRam(ram);
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return ram;
    }

    @Override
    public final Integer getPrice(final Document document) {
        Integer price = null;
        try {
            String priceString = document.select("div._1vC4OE._3qQ9m1").text();
            price = Integer.parseInt(Util.extractPrice(priceString));
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
    public final String getInternalStorage(final Document document) {
        return Util.extractRam(map.get("internal storage"));
    }

    @Override
    public final String getColor(final Document document) {
        return map.get("color");
    }

    @Override
    public final String getModelNumber(final Document document) {
        return map.get("model number");
    }

    @Override
    public final String getModelName(final Document document) {
        String name = null;
        try {
            name = document
                    .select("span._35KyD6")
                    .first()
                    .text();
            name = Util.extractName(name);
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return name;
    }

    @Override
    public final boolean isPhone() {
        int counter = 0;
        if (map == null || map.size() == ZERO)
            return false;
        for (String key : COMMON_FEATURES) {
            if (map.containsKey(key.toLowerCase())) {
                counter++;
            }
        }
        if (map.containsKey("isphone")) {
            return true;
        }

        return counter >= FIVE;
    }

    @Override
    public final boolean getAvailabilityStatus(Document document) {
        Elements elements = null;
        try {
            elements = document.getElementsByClass("_9-sL7L");
        } catch (Exception e) {

        }
        return elements == null || elements.size() == ZERO;
    }

    @Override
    public String getImageUrl(Document document) {
        String url = null;
        try {
            url = document.select("div._3BTv9X._3iN4zu").first().getElementsByTag("img").first().attr("src");
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return url;
    }

    public String getBrandName(Document document) {
        Elements elements = document.getElementsByClass("_1KHd47");
        Element element = elements.get(elements.size() - 2);
        String text = element.text().toLowerCase();
        int i = 0;
        while (i < text.length() && text.charAt(i) != ' ') {
            i++;
        }
        String bName = text.substring(0, i);
        bName = SPACE_SYMBOL + bName + SPACE_SYMBOL;
        bName = bName.toLowerCase().replace(XIAOMI_REDMI, REDMI).replace(MI_REDMI, REDMI);
        return bName.trim();
    }
}
