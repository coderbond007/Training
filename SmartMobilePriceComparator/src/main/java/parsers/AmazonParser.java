package parsers;

import clients.PrintStreamClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Util;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static constants.Constants.EMPTY_STRING;
import static constants.Constants.MI_REDMI;
import static constants.Constants.REDMI;
import static constants.Constants.SPACE_SYMBOL;
import static constants.Constants.THREE;
import static constants.Constants.XIAOMI_REDMI;
import static constants.Constants.ZERO;

@Deprecated
public class AmazonParser extends AbstractParser {
    private static final Logger logger = LoggerFactory.getLogger(AmazonParser.class);
    private static final String[] COMMON_FEATURES = {"OS", "RAM", "Batteries", "Item model number", "Form factor", "Whats in the box", "Other camera features"};
    private static final String AMAZON_RAM = "ram";
    private static final String AMAZON_INTERNAL_STORAGE = "";
    private static final String AMAZON_PRICE = "";
    private static final String AMAZON_COLOR = "colour";

    @Override
    public final Map<String, String> getFeatures(final Document document) {
        final long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        try {
            final Elements rows =
                    document
                            .select("div.column.col1").first()
                            .getElementsByTag("tr");

            if (rows == null || rows.size() == 0) return map;
            for (final Element row : rows) {
                if (row.getElementsByClass("label").size() == ZERO) {
                    continue;
                }
                try {
                    final Element key = row.getElementsByClass("label").first();
                    final Element value = row.getElementsByClass("value").first();
                    map.put(key.text().toLowerCase(), value.text().toLowerCase());
                } catch (Exception e) {
                    try {
                        e.printStackTrace(PrintStreamClient.getClient());
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
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
        final long endTime = System.currentTimeMillis();
        return map;
    }

    @Override
    public final String getRam(final Document document) {
        String ram = null;
        try {
            ram = Util.extractRam(map.get("ram"));
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
            Element element = document.getElementById("priceblock_ourprice");
            if (element == null) element = document.getElementById("priceblock_dealprice");
            String priceString = element.text();
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

//    @Override
//    public final String getInternalStorage(final Document document) {
//        String name = document.getElementById("productTitle").text().toLowerCase();
//        int cnt = 0;
//        for (int i = 0; i < name.length() - 2; i++) {
//            if (name.substring(i, i + 2).equals("gb")) {
//                cnt++;
//                if (cnt == 2) {
//                    String imemory = name.substring(i - 3, i);
//                    imemory = imemory.trim();
//                    imemory = imemory.replace(",", EMPTY_STRING);
//                    return Util.extractRam(imemory);
//                }
//            }
//        }
//        return null;
//
//    }

    @Override
    public final String getColor(final Document document) {
        return map.get("colour");
    }

    @Override
    public final String getModelNumber(final Document document) {
        return map.get("Item model number".toLowerCase());
    }

    @Override
    public final String getModelName(final Document document) {
        //System.out.println(document);
        String name = document.getElementById("productTitle").text();

        name = Util.extractName(name);
        return name;
    }

    @Override
    public final boolean getAvailabilityStatus(final Document document) {
        boolean status = false;
        try {
            String data = document.getElementById("availability").text().toLowerCase();
            if (data.contains("Currently unavilable".toLowerCase())) return false;
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public final String getImageUrl(final Document document) {
        String str = null;
        try {
            str =
                    document
                            .getElementById("imgTagWrapperId")
                            .getElementsByTag("img").first()
                            .attr("src");
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return str;
    }

    public String getBrandName(Document document) {
        String bname = null;
        try {
            Element element = document.getElementById("bylineInfo");
            String text = element.text();
            text = text.trim().toLowerCase();
            int i = 0;
            while (i < text.length() && text.charAt(i) != ' ') {
                i++;
            }
            String bName = text.substring(0, i);
            bName = SPACE_SYMBOL + bName + SPACE_SYMBOL;
            bName = bName.toLowerCase().replace(XIAOMI_REDMI, REDMI).replace(MI_REDMI, REDMI);
            return bName.trim();
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            return bname;
        }
    }

    @Override
    public final boolean isPhone() {
        int counter = ZERO;
        for (String features : COMMON_FEATURES) {
            if
            (map.containsKey(features.toLowerCase())) {
                counter++;
            }
        }
        return counter >= COMMON_FEATURES.length - THREE;
    }

    @Override
    public final String getInternalStorage(final Document document) {
        try {
            String name = document.getElementById("productTitle").text().toLowerCase();
            int cnt = 0;
            String ram1 = null;
            String ram2 = null;
            for (int i = 0; i < name.length() - 2; i++) {
                if (name.substring(i, i + 2).equals("gb")) {
                    cnt++;
                    if (cnt == 1) {
                        String imemory = name.substring(i - 3, i);
                        imemory = imemory.trim();
                        imemory = imemory.replace(",", EMPTY_STRING);
                        ram1 = Util.extractRam(imemory);
                    } else {
                        String imemory = name.substring(i - 3, i);
                        imemory = imemory.trim();
                        imemory = imemory.replace(",", EMPTY_STRING);
                        ram2 = Util.extractRam(imemory);
                    }
                }
            }
            if (ram1 != null && ram1.equals(getRam(document))) {
                return ram2;
            }
            if (ram2 != null && ram2.equals(getRam(document))) {
                return ram1;
            }
            int index = name.indexOf('+');
            if (index >= 0) {
                int p1 = index - 1;
                int p2 = index + 1;
                while (p1 >= 0 && Character.isDigit(name.charAt(p1))) {
                    p1--;
                }
                while (p2 < name.length() && Character.isDigit(name.charAt(p2))) {
                    p2++;
                }
                ram1 = name.substring(p1 + 1, index);
                ram2 = name.substring(index + 1, p2);
                if (ram1.equals(getRam(document)))
                    return ram2;
                if (ram2.equals(getRam(document))) {
                    return ram1;
                }
            }
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return null;

    }
}
