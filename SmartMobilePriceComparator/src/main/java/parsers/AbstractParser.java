package parsers;

import bean.MobilePhoneBean;
import clients.PrintStreamClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static constants.Constants.ADD_SYMBOL;
import static constants.Constants.EMPTY_STRING;
import static constants.Constants.MI_REDMI;
import static constants.Constants.REDMI;
import static constants.Constants.SPACE_SYMBOL;
import static constants.Constants.XIAOMI_REDMI;

abstract class AbstractParser implements Parser {
    protected Map<String, String> map;

    private MobilePhoneBean initializeBean(final Document document) {
        final MobilePhoneBean mobilePhoneBean = new MobilePhoneBean();
        StringBuilder stringBuilder = new StringBuilder();

        {
            String ram = getRam(document);
            String internalStorage = getInternalStorage(document);
            if (ram == null)
                ram = "0";
            if (internalStorage == null)
                internalStorage = "0";
            stringBuilder.append(ram);
            stringBuilder.append(ADD_SYMBOL);
            stringBuilder.append(internalStorage);
        }

        mobilePhoneBean.setRam(stringBuilder.toString());
        mobilePhoneBean.setColor(getColor(document));
        mobilePhoneBean.setInternalStorage(getInternalStorage(document));
        mobilePhoneBean.setModelName(getModelName(document));
        mobilePhoneBean.setModelNumber(getModelNumber(document));
        mobilePhoneBean.setPhone(isPhone());
        mobilePhoneBean.setPrice(getPrice(document));
        mobilePhoneBean.setAvailableStatus(getAvailabilityStatus(document));
        mobilePhoneBean.setImageURL(getImageUrl(document));
        mobilePhoneBean.setBrandName(getBrandName(document));
        mobilePhoneBean.setGenericName(getGenericName(document));
        return mobilePhoneBean;
    }

    public MobilePhoneBean parseData(final String pageSource) {
        MobilePhoneBean mobilePhoneBean = null;
        try {
            Document document = Jsoup.parse(pageSource);
            try {
                map = getFeatures(document);
                if (map == null) map = new HashMap<>();
                mobilePhoneBean = initializeBean(document);
            } catch (Exception e) {
                try {
                    e.printStackTrace(PrintStreamClient.getClient());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException();
        }
        return mobilePhoneBean;
    }

    @Override
    public String getGenericName(final Document document) {
        String name = getModelName(document).toLowerCase();
        name = SPACE_SYMBOL + name + SPACE_SYMBOL;
        name = name.toLowerCase()
                .replace(XIAOMI_REDMI, REDMI)
                .replace(MI_REDMI, REDMI);
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == '(') {
                name = name.substring(0, i);
                break;
            }
        }
        for (int i = 0; i < name.length() - 2; i++) {
            if (name.substring(i, i + 2).equals("gb")) {
                name = name.substring(0, i + 2);
                break;
            }
        }
        String ram = getRam(document);
        String internalStrorage = getInternalStorage(document);
        if (ram != null && internalStrorage != null) {
            name = name.replace(ram + internalStrorage + "gb", EMPTY_STRING);
            name = name.replace(ram + internalStrorage + " gb", EMPTY_STRING);
        }
        if (internalStrorage != null) {
            name = name.replace(internalStrorage + "gb", EMPTY_STRING);
            name = name.replace(internalStrorage + " gb", EMPTY_STRING);
        }
        if (ram != null) {
            name = name.replace(ram + "gb", EMPTY_STRING);
            name = name.replace(ram + " gb", EMPTY_STRING);
        }
        String color = getColor(document);
        color = color.toLowerCase().trim();
        name = name.replace(color, EMPTY_STRING);
        name = name.trim();

        name = " " + name;
        name = name.replace(" " + getBrandName(document) + " ", " ");
        name = " " + name;
        name = name.replace(" " + getBrandName(document) + " ", " ");
        name = getBrandName(document) + " " + name;
        return name;
    }
}