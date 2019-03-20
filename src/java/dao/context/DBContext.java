package dao.context;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {

    private static DBContext instance;

    private DBContext() {
    }

    public static DBContext getInstance() {
        if (instance == null) {
            instance = new DBContext();
        }
        return instance;
    }

    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }

    private final String serverName = "localhost";
    private final String dbName = "Fassenger";
    private final String portNumber = "1433";
    private final String userID = "sa";
    private final String password = "123";
}
