import java.sql.*;

public class SQLDAO {
    private Connection con;

    public SQLDAO() {
        this.con = SQLConnection();
    }

    public static Connection SQLConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/kailua", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection() {
        return con;
    }
}
