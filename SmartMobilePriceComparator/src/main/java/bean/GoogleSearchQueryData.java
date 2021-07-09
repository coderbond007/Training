package bean;

import java.util.Arrays;

import static constants.Constants.EMPTY_STRING;
import static constants.Constants.ZERO;

/**
 * Bean for the Google Search Query
 */
public final class GoogleSearchQueryData {
    private String site;
    private String[] searchKeywords;
    private boolean isValid;

    public GoogleSearchQueryData(final String site, final String[] searchKeywords) {
        this.site = site;
        if (this.site == null)
            this.site = EMPTY_STRING;
        isValid = searchKeywords != null && searchKeywords.length != ZERO;
        this.searchKeywords = searchKeywords;
    }

    public String getSite() {
        return site;
    }

    public String[] getSearchKeywords() {
        return searchKeywords;
    }

    public boolean isValid() {
        return isValid;
    }

    @Override
    public String toString() {
        return "GoogleSearchQueryData{" +
                "site='" + site + '\'' +
                ", searchKeywords=" + Arrays.toString(searchKeywords) +
                ", isValid=" + isValid +
                '}';
    }
}
