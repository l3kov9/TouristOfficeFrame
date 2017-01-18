import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBCommunication {
    static Connection conn=null;

    static Connection myConnection(){

        try{
            Class.forName("org.h2.Driver");
            conn= DriverManager.getConnection("jdbc:h2:tcp://localhost/~/TouristsInformationOffice","lekov","");
            return conn;
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
}
