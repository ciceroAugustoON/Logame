package gui.scenes;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.JsonNode;

import db.GameDAO;
import entity.Game;
import gui.AddGameViewController;
import integrations.Steam;
import integrations.SyncPlatform;
import integrations.Steam.AssetType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GameInfoController implements Initializable{

    private static Stage stage;

    public static void setStage(Stage stage) {
        GameInfoController.stage = stage;
    }

    @FXML
    private ComboBox<String> searchGame;

    @FXML
    private ImageView gameImage;

    @FXML
    private ChoiceBox<String> state;

    @FXML
    private Button addGame;

    private JsonNode platformNode = SyncPlatform.getPlatformInfo(AddGameViewController.PlataformSelected);
    private String searchText;
    private List<String> gameName;
    private List<Game> gameList;
    private Game selected;
    private Steam steam = new Steam(platformNode.get("apiKey").asText(), platformNode.get("userID").asText());
    // Limit search method call
    private long lasttime = 0;


    @FXML
    private void onSearchGameAction() {
        for (Game g : gameList) {
            if (searchGame.getSelectionModel().getSelectedItem() == g.getName()) {
                selected = g;
            }
        }
            
        try {
            String imgPath = Steam.getGameAsset(selected.getId(), AssetType.CAPSULE);
            Image img = new Image(imgPath, true);
            gameImage.setImage(img);
            gameImage.setPreserveRatio(true);
            gameImage.setFitWidth(160);
            gameImage.setFitHeight(230);
        } catch (Exception e) {
            e.printStackTrace();
        }
        state.setDisable(false);
        state.getItems().addAll("Playing", "Next", "Backlog", "Finished");
    }

    @FXML
    private void onSearchGameKeyPressed() {
        long time = System.currentTimeMillis();

        if (!state.isDisable()) {
            state.setDisable(true);
            state.getItems().clear();
        }

        searchText = searchGame.getEditor().getText();
        searchGame.hide();
        searchGame.getItems().clear();

        if ((time - lasttime) > 2000) {
            gameList = steam.searchSuggestion(searchText);
            gameName = new ArrayList<>();
            for (Game g : gameList) {
                gameName.add(g.getName());
            }
            ObservableList<String> observableList = FXCollections.observableList(gameName);
            searchGame.getItems().addAll(observableList);
            searchGame.show();
            lasttime = time;
        }
    }

    @FXML
    private void onStateAction(ActionEvent evt) {
        if (!state.getValue().equals("Finished")) {
            addGame.setDisable(false);
        }

        selected.setState(state.getValue());
    }

    @FXML
    private void onAddGameAction() {
        GameDAO.insertData(selected);
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        state.setOnAction(this::onStateAction);
    }
}
