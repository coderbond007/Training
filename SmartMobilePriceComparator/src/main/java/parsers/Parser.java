package parsers;

import bean.MobilePhoneBean;
import org.jsoup.nodes.Document;

import java.util.Map;

public interface Parser {
    MobilePhoneBean parseData(final String pageSource);

    Map<String, String> getFeatures(final Document document);

    String getRam(final Document document);

    Integer getPrice(final Document document);

    String getInternalStorage(final Document document);

    String getColor(final Document document);

    String getModelNumber(final Document document);

    String getModelName(final Document document);

    boolean getAvailabilityStatus(final Document document);

    String getImageUrl(final Document document);

    String getGenericName(final Document document);

    String getBrandName(final Document document);

    boolean isPhone();
}