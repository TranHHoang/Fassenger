package dao.context;

import app.exception.InternalException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public Connection getConnection() throws InternalException {
        try {
            String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, userID, password);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new InternalException(ex.getMessage());
        }
    }

    private final String serverName = "localhost";
    private final String dbName = "Fassenger";
    private final String portNumber = "1433";
    private final String userID = "sa";
    private final String password = "123";
}
