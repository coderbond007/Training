package clients;

import net.media.mnetcrawler.bean.UserAgentType;
import net.media.mnetcrawler.util.RandomUserAgentManager;
import net.media.mnetcrawler.util.UserAgentManager;

public class UserAgentManagerClient {
    private static UserAgentManager userAgentManager = null;

    public static UserAgentManager getClient() throws Exception {
        if (userAgentManager == null) {
            synchronized (UserAgentManager.class) {
                if (userAgentManager == null) {
                    userAgentManager = createClient();
                }
            }
        }
        return userAgentManager;
    }

    private static UserAgentManager createClient() throws Exception {
        UserAgentManager userAgentManager = new RandomUserAgentManager(UserAgentType.DESKTOP);
        return userAgentManager;
    }
}
