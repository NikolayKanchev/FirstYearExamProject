package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by bc on 09/05/2017.
 */
public class DBCon {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://52.57.136.74:3306/";
    private static final String DB_NAME = "nordic_motorhomes";
    private static final String USER = "Nikolay";
    private static final String PASS = "1234";

    public static Connection getConn()
    {
        Connection conn = null;
        try
        {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASS);
            return conn;

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


}
