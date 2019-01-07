package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection
{
    private final static String SQLCON = "jdbc:sqlite:dictionary.sqlite";

    public static Connection getConnection() throws SQLException, ClassNotFoundException
    {
        System.out.println("DATABASE CONNECTED!");
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(SQLCON);
    }
}
