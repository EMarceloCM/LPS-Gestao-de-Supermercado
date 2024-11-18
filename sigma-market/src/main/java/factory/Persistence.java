package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Persistence {
    private static Persistence instance = null;
    private Connection connection = null;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/marketDB?useSSL=false&amp;serverTimezone=America/Sao_Paulo";
    private static final String USER = "root";
    private static final String PASSWORD = "abc123";

    private Persistence() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error - Ao abrir conexão. " + ex);
        }
    }

    public static Connection getConnection() {
        if (instance == null) {
            instance = new Persistence();
        }
        return instance.connection;
    }

    public static void closeConnection() {
        if (instance != null && instance.connection != null) {
            try {
                instance.connection.close();
                instance = null;
            } catch (SQLException ex) {
                Logger.getLogger(Persistence.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}