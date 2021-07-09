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

public class PaytmMallParser extends AbstractParser {
    private static final String[] commonFeatures = {"secondary camera", "battery capacity", "camera", "os", "processor", "touch screen",};
    private static final String PAYTM_MALL_RAM = "ram";
    private static final String PAYTM_MALL_INTERNAL_STORAGE = "internal memory";
    private static final String PAYTM_MALL_PRICE = "price";
    private static final String PAYTM_MALL_COLOR = "color";
    Document tempDoc;

    @Override
    public Map<String, String> getFeatures(Document document) {
        // isCostSensible=true;
        tempDoc = document;
        Map<String, String> map = new HashMap<>();
        try {
            Elements rows = document.getElementsByClass("FqsW");
            try {
                Element price = document.getElementsByClass("_1V3w").first();
                map.put("price", price.text());
            } catch (Exception e) {
                try {
                    e.printStackTrace(PrintStreamClient.getClient());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                Element modelname = document.getElementsByClass("NZJI").first();
                String name = modelname.text();
                name = Util.extractName(name);
                map.put("modelename", name);
//                System.out.println(name);
            } catch (Exception e) {
                try {
                    e.printStackTrace(PrintStreamClient.getClient());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                Elements keywords = document.getElementsByClass("_333S");
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
//_333S

            for (Element row : rows) {
                try {
                    Element key =
                            row
                                    .getElementsByClass("w3LC")
                                    .first();
                    Element value =
                            row
                                    .getElementsByClass("_2LOI")
                                    .first();
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
        return map;
    }

    @Override
    public String getRam(Document document) {
        String ram = null;
        try {
            ram = Util.extractRam(map.get(PAYTM_MALL_RAM));
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
    public Integer getPrice(Document document) {
        Integer price = null;
        try {
            price = Integer
                    .parseInt(
                            Util.extractPrice(
                                    map.get("price")
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
    public String getInternalStorage(Document document) {
        return Util.extractRam(map.get("internal memory"));
    }

    @Override
    public String getColor(Document document) {
        return map.get("color");
    }

    @Override
    public String getModelNumber(Document document) {
        return map.get("model id");
    }

    @Override
    public String getModelName(Document document) {
        return map.get("modelename");
    }

    @Override
    public boolean getAvailabilityStatus(Document document) {
        try {
            Elements available = document.select("div._2CNI.UJ30");
            String avail = "false";
            if (available.size() == 0) {
                return true;
            }
            map.put("available", avail);
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public String getImageUrl(Document document) {
        String url = null;
        try {
            url = document
                    .getElementsByClass("_3v_O")
                    .first()
                    .attr("src");
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
//        System.out.println(url + " lol");
        return url;
    }

    public String getBrandName(Document document) {
        String text = map.get("brand");
        text = text.trim();
        int i = 0;
        while (i < text.length() && text.charAt(i) != ' ') {
            i++;
        }
        String bName = text.substring(0, i);
        bName = SPACE_SYMBOL + bName + SPACE_SYMBOL;
        bName = bName.toLowerCase().replace(XIAOMI_REDMI, REDMI).replace(MI_REDMI, REDMI);
        return bName.trim();
    }

    @Override
    public boolean isPhone() {
        int cnt = 0;
        for (String features : commonFeatures) {
            if
            (map.containsKey(features.toLowerCase())) {
                cnt++;
            }
        }
        if (map.containsKey("isphone")) {
//            System.out.println("yoo");
            return true;
        }

        return cnt >= commonFeatures.length - 3;
    }
}
