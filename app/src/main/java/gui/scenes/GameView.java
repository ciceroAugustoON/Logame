package gui.scenes;

import entity.Game;
import gui.util.Assets;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameView implements Initializable {

    @FXML
    private ImageView gamePortrait;
    @FXML
    private Label gameName;
    @FXML
    private Label gameDesc;

    private static Game g;

    public static void setGame(Game game) {
        System.out.println(game.toString() + "Dentro do setGame");
        g = game;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image imgCover = new Image(Assets.loadImage(g.getCover()));
        gamePortrait.setImage(imgCover);
        gameName.setText(g.getName());
        gameDesc.setText(g.getRelease() + "\n" + g.getGenre() + "\n" + g.getScope());
    }
}
