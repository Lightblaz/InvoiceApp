package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public static void getDBConnection() throws ClassNotFoundException , SQLException {
        Class.forName("com.mysql.jdbc.Driver"); //what if the driver doesn't exits
        //System.out.println("Published the driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //System.out.println("Connected");
    }


}
