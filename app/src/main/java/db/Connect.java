package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
    
    public static Connection getConnection() {
        Connection conn = null;
        try {

            String dbPath = Connect.class.getResource("/db/").getPath();

            String url = "jdbc:sqlite:"+dbPath + "gamelibrary.db";

            conn = DriverManager.getConnection(url);
            createTables(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    private static void createTables(Connection conn) {
        String games = "CREATE TABLE IF NOT EXISTS games (\n" +
                    "	id INT PRIMARY KEY,\n" +
                    "	name VARCHAR(200) NOT NULL,\n" +
                    "	realese YEAR,\n" +
                    "	genre VARCHAR(150),\n" +
                    "	scope VARCHAR(150)\n" +
                    ");";
        String unfinished = "CREATE TABLE IF NOT EXISTS unfinished (\n" +
                                    "	id INT PRIMARY KEY,\n" +
                                    "	game_id INT,\n" +
                                    "	state VARCHAR(10) NOT NULL,\n" +
                                    "        platform VARCHAR(100) NOT NULL,\n" +
                                    "	progress VARCHAR(65535),\n" +
                                    "	FOREIGN KEY(game_id) REFERENCES games(id) \n" +
                                    ");";
        String finished =  "CREATE TABLE IF NOT EXISTS finished (\n" +
                                    "	id INT PRIMARY KEY,\n" +
                                    "	game_id INT,\n" +
                                    "        platform VARCHAR(100) NOT NULL,\n" +
                                    "	progress VARCHAR(65535),\n" +
                                    "	date_finished DATE,\n" +
                                    "	time INT,\n" +
                                    "	FOREIGN KEY(game_id) REFERENCES games(id) \n" +
                                    ");";
                   
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(games);
            stmt.execute(unfinished);
            stmt.execute(finished);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
