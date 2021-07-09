package clients;

import net.media.mnetcrawler.util.UserAgentManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumCrawlClient {
    private static final String USER_AGENT_ARG = "--user-agent=";
    private static final String START_MAXIMISED_ARG = "--start-maximized";

    public static WebDriver getWebDriverClient() throws Exception {
        UserAgentManager userAgentManager = UserAgentManagerClient.getClient();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(USER_AGENT_ARG + userAgentManager.getUserAgent());
        chromeOptions.addArguments(START_MAXIMISED_ARG);
        return new ChromeDriver(chromeOptions);
    }
}
