package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Game;

public class GameDAO {
    
    public static void insertData(Game game) {
        String sql = "INSERT INTO Games(id, name, platform) VALUES(?,?,?)";
        String sqlState = "INSERT INTO Unfinished(rowid, game_id, state) VALUES(null, ?, ?)";
        

        try (Connection conn = Connect.getConnection(); PreparedStatement prepare = conn.prepareStatement(sql)) {

            prepare.setString(1, game.getId());
            prepare.setString(2, game.getName());
            prepare.setString(3, game.getPlatform());

            prepare.executeUpdate();
            System.out.println("O jogo foi registrado!");

            if (!game.getState().equals("Finished")) {
                try (PreparedStatement prepareState = conn.prepareStatement(sqlState)) {
                    prepareState.setString(1, game.getId());
                    prepareState.setString(2, game.getState());

                    prepareState.executeUpdate();
                    System.out.println("O estado do jogo foi registrado!");
                } catch (SQLException e) {
                    System.out.println(GameDAO.class.getName()+" Unfinished error! "+e.getMessage());
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(GameDAO.class.getName()+" "+e.getMessage());
        }
    }

    public static List<Game> selectData() {
        String sql = "SELECT * FROM Games";
        List<Game> gamesData = new ArrayList<>();

        try (Connection conn = Connect.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Game g = new Game(rs.getString("id"), rs.getString("name"), rs.getString("platform"));
                gamesData.add(g);
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(GameDAO.class.getName()+" "+e.getMessage());
        }
        return gamesData;
    }
}
