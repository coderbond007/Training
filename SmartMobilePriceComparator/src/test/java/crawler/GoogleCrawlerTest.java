package crawler;

import bean.GoogleSearchQueryData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static constants.Constants.MOBILE_NAMES_PATH;
import static constants.Constants.SPACE_SYMBOL;
import static constants.Constants.STORES;
import static crawler.GoogleCrawler.fetchSearchResults;

public class GoogleCrawlerTest {
    private List<String> mobileNames;
    private BufferedReader bufferedReader;
    private int NUMBER_OF_MOBILES = 1;

    @Before
    public void setUp() throws Exception {
        mobileNames = new ArrayList<>();
        bufferedReader = new BufferedReader(new FileReader(MOBILE_NAMES_PATH));
        int count = 0;
        while (bufferedReader.ready()) {
            String currentMobileName = bufferedReader.readLine();
            mobileNames.add(currentMobileName);
            ++count;
            if (count == NUMBER_OF_MOBILES) break;
        }
    }

    @After
    public void tearDown() throws Exception {
        bufferedReader.close();
        mobileNames.clear();
    }

    @Test
    public void fetchSearchResults_TEST() throws Exception {
        for (String currentMobile : mobileNames) {
            List<String> URLs = new ArrayList<>();
            for (String currentStore : STORES) {
                GoogleSearchQueryData queryData = new GoogleSearchQueryData(currentStore, currentMobile.split(SPACE_SYMBOL));
                List<String> currentResults = fetchSearchResults(queryData);
                if (currentResults != null && currentResults.size() > 0)
                    URLs.addAll(currentResults);
            }
            Assert.assertNotNull(URLs);
        }
    }
}