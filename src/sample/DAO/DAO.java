package sample.DAO;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    public Connection connection;
    public Connection getConnection() {
        final String username = "root";
        final String password = "root";
        final String URL = "jdbc:mysql://localhost:3306/";
        final String DRIVER = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
