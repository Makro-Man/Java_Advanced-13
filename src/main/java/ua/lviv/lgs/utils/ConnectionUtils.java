package ua.lviv.lgs.utils;

import org.apache.log4j.xml.DOMConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    private static String USER_NAME = "shop";
    private static String USER_PASSWORD = "1111";
    private static String URL = "jdbc:mysql://localhost:3306/shop";

    public static Connection openConnection() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        DOMConfigurator.configure("loggerConfig.xml");
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        return DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD);
    }

}
