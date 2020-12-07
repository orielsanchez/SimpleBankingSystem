package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connect {
    public static void connect(String filename) {
        Connection connection = null;
        try {
            String url = "jdbc:sqlite:C:/sqlite/db/" + filename;

            connection = DriverManager.getConnection(url);
            System.out.println("Connection to " + filename + " established.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
