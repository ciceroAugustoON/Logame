package gui.scenes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import db.GameDAO;
import entity.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GameList implements Initializable {

    @FXML
    private ScrollPane content;

    @FXML
    private GridPane listGrid;

    private static List<Game> listGames;
    private static String state;

    public static void setState(String state) {
        GameList.state = state;
        System.out.println(state);
    }
    
    public ScrollPane getScrollPane() {
        return content;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listGames = GameDAO.selectData(state);
        int gridColumn = 0;
        int gridRow = 0;
        
        for (Game game : listGames) {
            Pane gameCapsule = new Pane();
            gameCapsule.setPrefSize(200, 300);
            Image img = new Image(getClass().getResourceAsStream("/gui/imgs/gameassets/"+game.getId()+"_CAPSULE.jpg"));
            BackgroundImage imgBG = new BackgroundImage(
                img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
            );
            gameCapsule.setBackground(new Background(imgBG));

            GridPane.setRowIndex(gameCapsule, gridRow);
            GridPane.setColumnIndex(gameCapsule, gridColumn);

            listGrid.getChildren().add(gameCapsule);

            System.out.println("Jogo: "+game.getName()+" adicionado as coordenadas: "+gridColumn+", "+gridRow);

            gridColumn++;
            if (gridColumn == 3) {
                gridColumn = 0;
                gridRow++;
            }
        }
    }
}
