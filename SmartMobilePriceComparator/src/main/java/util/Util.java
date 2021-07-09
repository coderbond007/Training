package util;

import org.jsoup.helper.StringUtil;

import static constants.Constants.COMMA_SYMBOL;
import static constants.Constants.DOT_SYMBOL;
import static constants.Constants.EMPTY_STRING;
import static constants.Constants.ZERO;

public class Util {
    public static String extractName(String name) {
        if (name == null)
            return null;
        int i = 0;
        for (; i < name.length(); i++) {
            if (name.charAt(i) == '(') {
                break;
            }
        }
        name = name.substring(0, i);
        name = name.trim();
        return name;
    }

    public static String extractPrice(String price) {
        if (StringUtil.isBlank(price))
            return null;
        int index = price.indexOf(DOT_SYMBOL);
        if (index < ZERO) index = price.length();

        price = price
                .substring(ZERO, index)
                .trim()
                .replace(COMMA_SYMBOL, EMPTY_STRING);
        StringBuilder stringBuilder = new StringBuilder();
        int ptr = 0;
        while (ptr < price.length() && !Character.isDigit(price.charAt(ptr)))
            ++ptr;
        for (int currentIndex = ptr; currentIndex < price.length(); ++currentIndex) {
            final char currentCharacter = price.charAt(currentIndex);
            if (Character.isDigit(currentCharacter)) {
                stringBuilder.append(currentCharacter);
            } else {
                break;
            }
        }
        int length = stringBuilder.length();
        return length == 0 ? null : stringBuilder.toString();
    }

    public static String extractRam(String ram) {
        if (ram == null)
            return null;
        ram = ram.trim();
        int i = 0;
        for (; i < ram.length(); i++) {
            if ('0' > ram.charAt(i) || ram.charAt(i) > '9') {
                break;
            }
        }
        return ram.substring(0, i);
    }

}
