package gui;

import entity.Game;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.scenes.GameList;
import gui.scenes.GameView;
import gui.util.ViewUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainViewController implements Initializable{
    
    @FXML
    private Button btnPlaying;
    
    @FXML
    private Button btnNextToPlay;
    
    @FXML
    private Button btnBacklog;

    @FXML
    private Button btnCompleted;

    @FXML
    private ImageView addGame;

    @FXML
    private VBox tabs;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Parent addGameParent;

    private int tabSelected = -1;

    @FXML
    private void onBtnPlayingAction() {
        if (tabSelected != 0) {
            int oldSelect = tabSelected;
            GameList.setConfList("Playing", scroll, addGame);
            ScrollPane node;
            try {
                node = ViewUtils.loadFXML("/gui/scenes/GameList.fxml").load();
                scroll.setContent(node.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            tabSelected = 0;
            tabSelect(tabSelected);
            tabUnselect(oldSelect);
            
            if (!addGame.isVisible()) {
                addGame.setVisible(true);
            }
        }
        
    }

    @FXML
    private void onBtnNextToPlayAction() {
        if (tabSelected != 1) {
            int oldSelect = tabSelected;
            GameList.setConfList("Next", scroll, addGame);
            ScrollPane node;
            try {
                node = ViewUtils.loadFXML("/gui/scenes/GameList.fxml").load();
                scroll.setContent(node.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }

            tabSelected = 1;
            tabSelect(tabSelected);
            tabUnselect(oldSelect);
            
            if (!addGame.isVisible()) {
                addGame.setVisible(true);
            }
        }
    }

    @FXML
    private void onBtnBacklogAction() {
        if (tabSelected != 2) {
            int oldSelect = tabSelected;
            GameList.setConfList("Backlog", scroll, addGame);
            ScrollPane node;
            try {
                node = ViewUtils.loadFXML("/gui/scenes/GameList.fxml").load();
                scroll.setContent(node.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }

            tabSelected = 2;
            tabSelect(tabSelected);
            tabUnselect(oldSelect);
            
            if (!addGame.isVisible()) {
                addGame.setVisible(true);
            }
        }
    }

    @FXML
    private void onBtnCompletedAction() {
        if (tabSelected != 3) {
            int oldSelect = tabSelected;
            GameList.setConfList("Finished", scroll, addGame);
            ScrollPane node;
            try {
                node = ViewUtils.loadFXML("/gui/scenes/GameList.fxml").load();
                scroll.setContent(node.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }

            tabSelected = 3;
            tabSelect(tabSelected);
            tabUnselect(oldSelect);
            
            if (!addGame.isVisible()) {
                addGame.setVisible(true);
            }
        }
    }

    @FXML
    private void onBtnAddGameAction() {
        Stage stage = ViewUtils.createNewStage("/gui/AddGameView.fxml");
        AddGameViewController.setStage(stage);
        // Steam.setUserID(SyncPlataform.getUserID());
    }

    public static void setGameView(Game game, ScrollPane scrollPane, ImageView addGameButton) {
        try {
            GameView.setGame(game);
            FXMLLoader loader = ViewUtils.loadFXML("/gui/scenes/GameView.fxml");
            ScrollPane content = loader.load();
            scrollPane.setContent(content);
            addGameButton.setVisible(false);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tabSelect(int tab) {
        switch (tab) {
            case 0:
                btnPlaying.getStyleClass().add("button_onclick");
                break;
            case 1:
                btnNextToPlay.getStyleClass().add("button_onclick");
                break;
            case 2:
                btnBacklog.getStyleClass().add("button_onclick");
                break;
            case 3:
                btnCompleted.getStyleClass().add("button_onclick");
                break;
            default:
                System.out.println("Invalid Option");
                break;
        }
    }
    private void tabUnselect(int tab) {
        switch (tab) {
            case 0:
                btnPlaying.getStyleClass().remove("button_onclick");
                break;
            case 1:
                btnNextToPlay.getStyleClass().remove("button_onclick");
                break;
            case 2:
                btnBacklog.getStyleClass().remove("button_onclick");
                break;
            case 3:
                btnCompleted.getStyleClass().remove("button_onclick");
                break;
            default:
                break;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(MainViewController.class.getResource("/gui/fonts/Inter/Inter.ttf").toExternalForm(), 14);
    }

}
