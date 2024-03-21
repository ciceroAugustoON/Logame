package gui;

import db.GameDAO;
import entities.Game;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.scenes.GameListViewController;
import gui.scenes.GameViewController;
import gui.util.ViewUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainViewController implements Initializable {

    @FXML
    private Button playingBtn;
    @FXML
    private Button nextToPlayBtn;
    @FXML
    private Button backlogBtn;
    @FXML
    private Button completedBtn;
    @FXML
    private Button addGameBtn;
    @FXML
    private VBox tabsVBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Parent addGameParent;

    private Button tabSelected;

    @FXML
    private void onPlayingBtnAction() {
        loadGameListView(playingBtn, "Playing");
    }

    @FXML
    private void onNextToPlayBtnAction() {
        loadGameListView(nextToPlayBtn, "Next");
    }

    @FXML
    private void onBacklogBtnAction() {
        loadGameListView(backlogBtn, "Backlog");
    }

    @FXML
    private void onCompletedBtnAction() {
        loadGameListView(completedBtn, "Finished");
    }

    @FXML
    private void onAddGameBtnAction() {
        Stage stage = ViewUtils.createNewStage(UniversalController.loadAddGameView(), "Add Game");
        AddGameViewController.setStage(stage);
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public void loadGameListView(Button tabSelected, String state) {
        if (this.tabSelected != null) {
            this.tabSelected.getStyleClass().remove("button_onclick");
        }
        
        this.tabSelected = tabSelected;
        this.tabSelected.getStyleClass().add("button_onclick");

        ScrollPane sp = (ScrollPane)UniversalController.loadGameListView(state);

        scrollPane.setContent(sp.getContent());
    }

    public void loadGameView(int gameId) {
        try {
            Game g = GameDAO.selectGameData(gameId);
            GameViewController.setGame(g);
            FXMLLoader loader = ViewUtils.loadFXML("/gui/scenes/GameView.fxml");
            ScrollPane content = loader.load();
            scrollPane.setContent(content);
            // addGameBtn.setVisible(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.getStylesheets().add("/gui/scenes/GameListStyle.css");
        Font.loadFont(MainViewController.class.getResource("/gui/fonts/Inter/Inter.ttf").toExternalForm(), 14);
    }

}