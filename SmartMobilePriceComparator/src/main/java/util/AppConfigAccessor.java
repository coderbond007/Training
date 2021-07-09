package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

import static constants.Constants.PATH;

/**
 * AppConfigAccessor initialize the environment for the app to run
 */
public class AppConfigAccessor {
    private static final String CONFIG_PATH_SUFFIX = "/src/main/resources/conf/app.conf";
    private static Properties properties = null;

    public static void initialize() throws IOException {
        if (properties == null)
            properties = new Properties();

        Reader reader = new FileReader(new File(PATH + CONFIG_PATH_SUFFIX));
        properties.load(reader);
    }

    public static String getValue(final String key) throws IOException {
        if (properties == null)
            initialize();
        return properties.getProperty(key);
    }

    public static void getAllProperties() throws IOException {
        if (properties == null)
            initialize();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            System.out.println(entry.getKey() + " :: " + entry.getValue());
        }
    }
}
