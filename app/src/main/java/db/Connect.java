package db;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    
    public static Connection getConnection() {
        Connection conn = null;
        try {

            URI dbPath = Connect.class.getResource("/db/gamelibrary.db").toURI();

            String url = "jdbc:sqlite:"+dbPath.getPath();

            conn = DriverManager.getConnection(url);
            System.out.println("Conexão com o banco de dados concluído.");
        } catch (URISyntaxException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
