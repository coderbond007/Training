package clients;


import util.AppConfigAccessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static constants.Constants.DB_DATABASE_NAME;
import static constants.Constants.DB_PASSWORD;
import static constants.Constants.DB_USER_NAME;
import static constants.Constants.JDBC_DRIVER_PREFIX;
import static constants.Constants.PORT_NUMBER;
import static constants.Constants.SLASH_SYMBOL;

public class DBClient {
    private static final String UNICODE_CHARACTER_ENCODING = "useUnicode=true&characterEncoding=UTF-8";
    private static final String BATCH_STATEMENTS_CONFIG = "rewriteBatchedStatements=true";
    private static Connection connection = null;
    private static String USERNAME = null;
    private static String PASSWORD = null;
    private static String DATABASE_NAME = null;
    private static String JDBC_DRIVER = null;

    public static Statement getStatement() throws SQLException, IOException {
        Statement statement = null;
        if (connection == null) {
            createConnection();
        }
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
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
            try {
                e.printStackTrace(PrintStreamClient.getClient());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
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
        USERNAME = AppConfigAccessor.getValue(DB_USER_NAME);
        PASSWORD = AppConfigAccessor.getValue(DB_PASSWORD);
        DATABASE_NAME = AppConfigAccessor.getValue(DB_DATABASE_NAME);
        JDBC_DRIVER = JDBC_DRIVER_PREFIX + PORT_NUMBER + SLASH_SYMBOL + DATABASE_NAME;
        System.out.println(JDBC_DRIVER);
    }

    public static void initiateConnection() throws IOException, SQLException {
        createConnection();
    }
}
