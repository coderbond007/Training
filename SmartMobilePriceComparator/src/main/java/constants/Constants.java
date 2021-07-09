package constants;

public class Constants {
    public static final String PATH = System.getProperty("user.dir");
    public static final String MOBILE_NAMES_PATH = PATH + "/data/mobilenames.txt";

    // App config related constants

    // MySQL constants
    public static final String DB_USER_NAME = "username";
    public static final String DB_PASSWORD = "password";
    public static final String DB_DATABASE_NAME = "database_name";
    public static final String JDBC_DRIVER_PREFIX = "jdbc:mysql://localhost:";
    public static final int PORT_NUMBER = 3306;

    // Symbols
    public static final String SLASH_SYMBOL = "/";
    public static final String ADD_SYMBOL = "+";
    public static final String SPACE_SYMBOL = " ";
    public static final String EMPTY_STRING = "";
    public static final String DOT_SYMBOL = ".";
    public static final String COMMA_SYMBOL = ",";
    public static final String PERCENTAGE_SYMBOL = "%";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String QUESTION_MARK_SYMBOL = "?";
    public static final String AND_SYMBOL = "&";

    // MemorySpecific Details
    public static final String GB = "gb";
    public static final String MB = "mb";
    public static final String TB = "tb";

    // Crawler related constants
    public static final String CRAWLER_APP_NAME = "Pradyumn-App";

    // Integer constants
    public static final int ZERO = 0;
    public static final int THREE = 3;
    public static final int FIVE = 5;

    // Temp variables
    public static final String[] STORES = new String[]{"flipkart.com", "amazon.in", "paytmmall.com", "snapdeal.com", "tatacliq.com"};
    public static final String[] MATCHER = new String[]{"flipkart", "amazon", "paytmmall", "snapdeal", "tatacliq"};
    public static final String[] IMAGE_LINKS = new String[]{"../image/flipkart.jpg", "../image/amazon.png", "../image/paytm.png", "../image/snapdeal.png", "../image/tata_cliq.png"};

    public static final boolean USE_MNET_CRAWLER = true;


    // Xiomi Use case
    public static final String XIAOMI_REDMI = " xiaomi ";
    public static final String MI_REDMI = " mi ";
    public static final String REDMI = " redmi ";
}
