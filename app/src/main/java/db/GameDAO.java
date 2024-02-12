package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Game;
import entity.Instance;
import java.sql.Date;

public class GameDAO {
    
    public static void insertData(Game game, Instance i) {
        String sql = "INSERT INTO games(id, name, realese, genre, scope) VALUES(?,?,?,?,?)";
        String sqlState = "INSERT INTO unfinished(game_id, state, platform) VALUES(?, ?, ?)";
        
        int id = getRecordNumber("games") + 1;

        try (Connection conn = Connect.getConnection(); PreparedStatement prepare = conn.prepareStatement(sql)) {
            
            prepare.setInt(1, id);
            prepare.setString(2, game.getName());
            prepare.setString(3, game.getRelease());
            prepare.setString(4, game.getGenre());
            prepare.setString(5, game.getScope());

            prepare.executeUpdate();

            if (!i.getState().equals("Finished")) {
                System.out.println("nooooooo");
                try (PreparedStatement stmt = conn.prepareStatement(sqlState)) {
                    stmt.setInt(1, id);
                    stmt.setString(2, i.getState());
                    stmt.setString(3, i.getPlatform());
                    
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(GameDAO.class.getName()+" Unfinished error! "+e.getMessage());
                }
            } else {
                sqlState = "INSERT INTO finished(game_id, platform, date_finished, time) VALUES(?, ?, ?, ?)";
                
                try (PreparedStatement stmt = conn.prepareStatement(sqlState)){
                    stmt.setInt(1, id);
                    stmt.setString(2, i.getPlatform());
                    stmt.setDate(3, Date.valueOf(i.getFinishedDate()));
                    stmt.setInt(4, i.getTime());
                    
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(GameDAO.class.getName()+" Finished error! "+e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(GameDAO.class.getName()+" "+e.getMessage());
        }
    }

    public static List<Game> selectData(String state) {
        String sql = "SELECT DISTINCT g.*, u.state FROM games as g JOIN unfinished as u ON g.id = u.game_id WHERE u.state = ?";
        
        if (state.equals("Finished")) {
            sql = "SELECT DISTINCT g.* FROM games as g JOIN finished as f ON g.id = f.game_id";
        }
        
        List<Game> games = new ArrayList<>();
        
        try (Connection conn = Connect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (!state.equals("Finished")) {
                stmt.setString(1, state);
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Game g = new Game(rs.getInt("id"), rs.getString("name"));
                g.setRealese(rs.getString("realese"));
                g.setGenre(rs.getString("genre"));
                g.setScope(rs.getString("scope"));
                System.out.println(g);
                games.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return games;
    }
    
    public static Game selectGameData(int id) {
        Game g;
        String sqlG = "SELECT g.* FROM games as g WHERE g.id = " + id;
        String sqlU = "SELECT u.* FROM unfinished as u WHERE u.game_id = " + id;
        String sqlF = "SELECT u.* FROM finished as f WHERE f.game_id = " + id;
        
        try (Connection conn = Connect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlG)) {
            ResultSet rs = stmt.executeQuery();
            
            g = new Game(rs.getInt("id"), rs.getString("name"));
            g.setRealese(rs.getString("realese"));
            g.setGenre(rs.getString("genre"));
            g.setScope(rs.getString("scope"));
            
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlU)) {
                ResultSet rs2 = stmt2.executeQuery();
                
                List<Instance> instances = new ArrayList<>();
                while (rs2.next()) {
                    Instance i = new Instance(rs2.getString("platform"), rs2.getString("state"));
                    instances.add(i);
                }
                g.setInstances(instances);
            }
            
            try (PreparedStatement stmt3 = conn.prepareStatement(sqlF)) {
                ResultSet rs3 = stmt3.executeQuery();
                
                while (rs3.next()) {
                    Instance i = new Instance(rs3.getString("platform"), "Finished", rs3.getDate("date_finished").toLocalDate(), rs3.getInt("time"));
                    g.addInstances(i);
                }
            }
            
            return g;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public static int getRecordNumber(String tablename) {
            int recordNumber = 0;
            String sql = "SELECT COUNT(*) FROM " + tablename;
            
            try (Connection conn = Connect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                
                recordNumber = rs.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            return recordNumber;
        }
}
