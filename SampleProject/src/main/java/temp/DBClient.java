package temp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class DBClient {
    public static final int PORT_NUMBER = 8080;
    private static Connection connection = null;
    private static String USERNAME = "root";
    private static String PASSWORD = "pradyumn";
    private static String DATABASE_NAME = "testdb";
    private static String JDBC_DRIVER = null;
    public static final String JDBC_DRIVER_PREFIX = "jdbc:mysql://localhost:";
    private static final String UNICODE_CHARACTER_ENCODING = "useUnicode=true&characterEncoding=UTF-8";
    private static final String BATCH_STATEMENTS_CONFIG = "rewriteBatchedStatements=true";


    public static Statement getStatement() throws SQLException, IOException {
        Statement statement = null;
        if (connection == null) {
            createConnection();
        }
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static PreparedStatement getPreparedStatement(final String query) throws SQLException, IOException {
        PreparedStatement preparedStatement = null;
        if (connection == null) {
            createConnection();
        }
        try {
            preparedStatement = connection.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    private static void createConnection() throws SQLException, IOException {
        initializeConstants();
        if (connection == null) {
            synchronized (Connection.class) {
                if (connection == null) {
                    connection = DriverManager.getConnection(JDBC_DRIVER, USERNAME, PASSWORD);
                }
            }
        }
    }

    private static void initializeConstants() throws IOException {
        JDBC_DRIVER = JDBC_DRIVER_PREFIX + PORT_NUMBER + "/" + DATABASE_NAME;
    }

    public static void initiateConnection() throws IOException, SQLException {
        createConnection();
    }
}
