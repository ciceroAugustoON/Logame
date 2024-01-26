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

public class GameView implements Initializable{
    
    @FXML
    private ImageView gamePortrait;
    
    @FXML
    private Label gameName;
    
    @FXML
    private Label gameDesc;
    
    private static Game g;
    
    public static void setGame(Game game) {
        g = game;
    }
    
    public void load() {
        File img = new File(Assets.loadImage(g.getId()+"_CAPSULE"));
        Image gameCapsule = new Image(img.toURI().toString());
        gamePortrait.setImage(gameCapsule);
        gameName.setText(g.getName());
        gameDesc.setText(g.descGame());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();
    }
    
}
