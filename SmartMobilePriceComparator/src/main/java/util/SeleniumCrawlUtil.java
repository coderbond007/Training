package util;

import clients.SeleniumCrawlClient;
import org.openqa.selenium.WebDriver;

import static crawler.GoogleCrawler.randomSleep;

public class SeleniumCrawlUtil {
    public static String getPageSource(final String URL) throws Exception {
        final WebDriver webDriver = SeleniumCrawlClient.getWebDriverClient();
        webDriver.get(URL);
        webDriver.manage().window().maximize();
        String pageSource = webDriver.getPageSource();
        webDriver.quit();
        randomSleep();
        return pageSource;
    }
}
