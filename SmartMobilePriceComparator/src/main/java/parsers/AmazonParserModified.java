package parsers;

import clients.PrintStreamClient;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Util;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static constants.Constants.EMPTY_STRING;
import static constants.Constants.GB;
import static constants.Constants.MI_REDMI;
import static constants.Constants.REDMI;
import static constants.Constants.SPACE_SYMBOL;
import static constants.Constants.THREE;
import static constants.Constants.XIAOMI_REDMI;
import static constants.Constants.ZERO;

public class AmazonParserModified extends AbstractParser {
    // Global Selectors Constants
    public static final String COMPARE_TABLE_ID = "HLCXComparisonTable";
    public static final String PRICE_SELECTOR_FIRST = "#comparison_price_row > td.comparison_baseitem_column > span";
    public static final String PRICE_SELECTOR_SECOND = "tbody > tr:nth-child(2) > td:nth-child(2) > span";
    private static final String[] COMMON_FEATURES = {"OS", "ram", "Batteries", "Item model number", "Form factor", "Whats in the box", "Other camera features"};
    private static final String PRODUCT_TITLE_ID = "productTitle";
    private static final String THIS_ITEM_SELECTOR = "#comparison_title > span.a-size-base.a-color-base.a-text-bold";
    // First logic constants
    private static final String THIS_ITEM_EXPECTED_VALUE = "This item";
    private static final String CHECK_TITLE_SELECTOR = "#comparison_title > span:nth-child(2)";
    private static final String ATTRIBUTES_CLASS = "comparison_other_attribute_row";
    private static final String PROPERTY_KEY_SELECTOR = "th > span";
    private static final String PROPERTY_VALUE_SELECTOR = "td.comparison_baseitem_column > span";
    // Second logic constants
    private static final String YOU_ARE_VIEWING_SELECTOR = "tbody > tr.comparison_table_image_row.border-none > th:nth-child(2) > div:nth-child(2) > div > span > strong";
    private static final String MOBILE_TITLE_DATA_CHECKER = "tbody > tr.comparison_table_image_row.border-none > th:nth-child(2) > div:nth-child(2) > div > span";
    private static final String YOU_ARE_VIEWING_VALUE = "You are viewing:";
    private static final String KEY_SELECTOR_SUBSTITUTION_NEEDED = "tbody > tr:nth-child($value$) > th > div > div.attribute-heading-icon-label-wrapper > div.attribute-heading-label > span";
    private static final String VALUE_SELECTOR_SUBSTITUTION_NEEDED = "tbody > tr:nth-child($value$) > td.base-item-column";
    private static final String SUBSTITUTION_VALUE = "$value$";

    // Map keys for storing all specs
    private static final String PRICE_KEY = "price";
    private static final String MODEL_NAME_KEY = "modelNameTitle";

    private static final String[] RAM_POSSIBLE_VALUES = new String[]{"ram", "internal memory"};
    private static final String[] INTERNAL_STORAGE_POSSIBLE_VALUES = new String[]{"flash memory", "inbuilt storage", "inbuilt storage (in gb)"};

    private static final String RAM_KEY = "ram";
    private static final String INTERNAL_STORAGE_KEY = "internal storage";

    private Map<String, String> map = null;

    @Override
    public Map<String, String> getFeatures(Document document) {
        try {
            map = fetchDataUsingTableFormatFirst(document);
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (map == null) {
            try {
                map = fetchDataUsingTableFormatSecond(document);
            } catch (Exception e) {
                try {
                    e.printStackTrace(PrintStreamClient.getClient());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        map = addBasicsFromTable(map, document);
        if (map == null)
            throw new RuntimeException();
        return map;
    }

    private Map<String, String> addBasicsFromTable(Map<String, String> map, Document document) {
        final long startTime = System.currentTimeMillis();

        if (map == null)
            map = new HashMap<>();
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
                    map.put(key.text().toLowerCase().trim(), value.text().toLowerCase().trim());
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

    private Map<String, String> fetchDataUsingTableFormatFirst(final Document document) {
        Map<String, String> specs = new HashMap<>();
        Element comparisonTable = document.getElementById(COMPARE_TABLE_ID);
        String thisItem =
                comparisonTable
                        .select(THIS_ITEM_SELECTOR)
                        .text();
        if (!thisItem.equalsIgnoreCase(THIS_ITEM_EXPECTED_VALUE)) {
            throw new RuntimeException();
        }

        String name = document.getElementById(PRODUCT_TITLE_ID).text();
        String checkTitle = comparisonTable.select(CHECK_TITLE_SELECTOR).text();
        String price = comparisonTable.select(PRICE_SELECTOR_FIRST).text();
        if (!checkTitle.equalsIgnoreCase(name)) {
            throw new RuntimeException();
        }
        specs.put(MODEL_NAME_KEY, name);
        specs.put(PRICE_KEY, price);

        Elements attributes = comparisonTable.getElementsByClass(ATTRIBUTES_CLASS);
        for (Element attribute : attributes) {
            String propertyName = attribute.select(PROPERTY_KEY_SELECTOR).text().trim().toLowerCase();
            String propertyValue = attribute.select(PROPERTY_VALUE_SELECTOR).text().trim().toLowerCase();
            specs.put(propertyName, propertyValue);
        }
        specs = generalizeSpecs(specs);
        return specs;
    }

    private Map<String, String> fetchDataUsingTableFormatSecond(final Document document) {
        Map<String, String> specs = new HashMap<>();
        Element comparisonTable = document.getElementById(COMPARE_TABLE_ID);
        String youAreViewing = comparisonTable.select(YOU_ARE_VIEWING_SELECTOR).text();
        if (!youAreViewing.equalsIgnoreCase(YOU_ARE_VIEWING_VALUE)) {
            throw new RuntimeException();
        }
        String name = document.getElementById(PRODUCT_TITLE_ID).text();
        String mobileTitleData = comparisonTable.select(MOBILE_TITLE_DATA_CHECKER).text();
        if (!mobileTitleData.toLowerCase().contains(name.toLowerCase())) {
            throw new RuntimeException();
        }
        String price = comparisonTable.select(PRICE_SELECTOR_SECOND).text();
        specs.put(MODEL_NAME_KEY, name);
        specs.put(PRICE_KEY, price);
        for (int iter = 0; iter < 100; ++iter) {
            try {
                String key = comparisonTable.select(KEY_SELECTOR_SUBSTITUTION_NEEDED.replace(SUBSTITUTION_VALUE, EMPTY_STRING + iter)).text().trim().toLowerCase();
                String value = comparisonTable.select(VALUE_SELECTOR_SUBSTITUTION_NEEDED.replace(SUBSTITUTION_VALUE, EMPTY_STRING + iter)).text().trim().toLowerCase();
                specs.put(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        specs = generalizeSpecs(specs);
        return specs;
    }

    private Map<String, String> generalizeSpecs(Map<String, String> specs) {
        if (specs == null || specs.size() == 0)
            return null;
        Set<String> keys = specs.keySet();
        if (keys.contains(EMPTY_STRING)) {
            specs.remove(EMPTY_STRING);
            keys.remove(EMPTY_STRING);
        }
        String ram = getGeneralValue(RAM_POSSIBLE_VALUES, specs);
        String internalStorage = getGeneralValue(INTERNAL_STORAGE_POSSIBLE_VALUES, specs);
        String price = specs.get(PRICE_KEY);

        specs.put(RAM_KEY, ram);
        specs.put(INTERNAL_STORAGE_KEY, internalStorage);
        specs.put(PRICE_KEY, price);
        return specs;
    }

    private String getGeneralValue(final String[] possibleValues, final Map<String, String> specs) {
        String value = null;
        for (String possibleKey : possibleValues) {
            if (value == null)
                value = specs.get(possibleKey);
        }
        if (value == null) throw new RuntimeException();
        return value;
    }


    @Override
    public String getRam(final Document document) {
        String ram = null;
        try {
            ram = Util.extractRam(map.get(RAM_KEY));
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
    public Integer getPrice(final Document document) {
//        String price = element.getElementsByClass("a-price-whole").size() != 0 ? element.getElementsByClass("a-price-whole").first().text() :
//                element.getElementsByClass("a-color-price").size() != 0 ? element.getElementsByClass("a-color-price").first().text() :
//                        element.getElementsByClass("a-size-base a-color-price s-price a-text-bold").size()!=0?element.getElementsByClass("a-size-base a-color-price s-price a-text-bold").first().text():element.getElementsByClass("a-size-base a-color-price s-price a-text-bold").first().text();
        String price = Util.extractPrice(map.get(PRICE_KEY));
        if (price == null) {
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
        return price == null ? null : Integer.parseInt(price);
    }

    @Override
    public String getInternalStorage(final Document document) {
        String storage = map.get(INTERNAL_STORAGE_KEY);
        if (storage.equalsIgnoreCase("-"))
            storage = null;
        if (storage == null) {
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
        }
        if (storage != null)
            storage = storage.toLowerCase().replace(GB, EMPTY_STRING).trim();
        return storage;
    }

    @Override
    public String getColor(final Document document) {
        String color = null;
        try {
            color = map.get("colour");
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return color;
    }

    @Override
    public String getModelNumber(final Document document) {
        String modelNumber = null;
        try {
            modelNumber = map.get("Item model number".toLowerCase());
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return modelNumber;
    }

    @Override
    public String getModelName(final Document document) {
        String name = null;
        try {
            name =
                    document
                            .getElementById(PRODUCT_TITLE_ID)
                            .text();
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
    public boolean getAvailabilityStatus(final Document document) {
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
    public String getImageUrl(final Document document) {
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

    @Override
    public String getBrandName(final Document document) {
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
            return bname;
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return StringUtil.isBlank(bname) ? null : bname.trim();
    }

    @Override
    public boolean isPhone() {
        int counter = ZERO;
        for (String features : COMMON_FEATURES) {
            if
            (map.containsKey(features.toLowerCase())) {
                counter++;
            }
        }
        return counter >= COMMON_FEATURES.length - THREE;
    }
}
