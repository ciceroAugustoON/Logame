package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.scenes.GameInfo;
import gui.scenes.GameList;
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
    private Button conf;

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
            GameList.setState("Playing");
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
        }
        
    }

    @FXML
    private void onBtnNextToPlayAction() {
        if (tabSelected != 1) {
            int oldSelect = tabSelected;
            GameList.setState("Next");
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
        }
    }

    @FXML
    private void onBtnBacklogAction() {
        if (tabSelected != 2) {
            int oldSelect = tabSelected;
            GameList.setState("Backlog");
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
        }
    }

    @FXML
    private void onBtnCompletedAction() {
        System.out.println("Ainda em construção");
    }

    @FXML
    private void onBtnAddGameAction() {
        Stage stage = ViewUtils.createNewStage("/gui/AddGameView.fxml");
        GameInfo.setStage(stage);
        // Steam.setUserID(SyncPlataform.getUserID());
    }

    @FXML
    private void onBtnConfAction() {
        if (tabSelected != 4) {
            int oldSelect = tabSelected;
            tabSelected = 4;
            try {
                FXMLLoader loader = ViewUtils.loadFXML("/gui/scenes/SyncTab.fxml");
                ScrollPane content = loader.load();
                scroll.setContent(content.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            tabSelect(tabSelected);
            tabUnselect(oldSelect);
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
            case 4:
                conf.getStyleClass().add("button_onclick");
                break;
            default:
                System.out.println("Aba inexistente");
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
            case 4:
                conf.getStyleClass().remove("button_onclick");
                break;
            default:
                System.out.println("Aba inexistente");
                break;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(MainViewController.class.getResource("/gui/fonts/Inter/Inter.ttf").toExternalForm(), 14);
    }

}
