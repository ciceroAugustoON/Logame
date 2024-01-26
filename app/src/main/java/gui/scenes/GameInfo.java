package gui.scenes;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.fasterxml.jackson.databind.JsonNode;

import db.GameDAO;
import entity.Game;
import gui.AddGameViewController;
import gui.util.Assets;
import integrations.Steam;
import integrations.SyncPlatform;
import integrations.Steam.AssetType;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameInfo implements Initializable {

    @FXML
    private ComboBox<String> searchGame;

    @FXML
    private ImageView gameImage;

    @FXML
    private ChoiceBox<String> state;

    @FXML
    private Button addGame;

    private static Stage stage;

    public static void setStage(Stage stage) {
        GameInfo.stage = stage;
    }

    private final JsonNode platformNode = SyncPlatform.getPlatformInfo(AddGameViewController.PlatformSelected);
    private String searchText;
    private List<Game> gameList;
    private Game selected;
    private Steam steam = new Steam(platformNode.get("apiKey").asText(), platformNode.get("userID").asText());
    private String capsule;
    private String icon;
    // Limit search method call
    private ScheduledExecutorService limit = Executors.newScheduledThreadPool(2);

    @FXML
    private void onSearchGameAction() {
        // Getting selected game
        for (Game g : gameList) {
            if (searchGame.getSelectionModel().getSelectedItem().equals(g.getName())) {
                selected = g;
            }
        }
        // Loading the game capsule and displaying in the panel
        try {
            capsule = Steam.getGameAsset(selected.getId(), AssetType.CAPSULE);
            icon = Steam.getIcon(selected.getId(), selected.getIconURL());
            gameImage.setImage(new Image(capsule, true));
            
            System.out.println(capsule);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // After the user selects the game, the combobox becomes enabled for selecting the state
        state.setDisable(false);
        state.getItems().addAll("Playing", "Next", "Backlog", "Finished");
    }

    @FXML
    private void onSearchGameKeyPressed() {

        searchGame.hide();
        searchGame.getItems().clear();

        searchText = searchGame.getEditor().getText();

        limit.schedule(() -> this.searchForGames(), 2, TimeUnit.SECONDS);

        if (!state.isDisable()) {
            state.setDisable(true);
            state.getItems().clear();
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
        if (capsule != null) {
            Assets.saveImage(capsule, selected.getId() + "_CAPSULE");
        }
        if (icon != null) {
            Assets.saveImage(icon, selected.getId() + "_ICON");
        }
        stage.close();
    }

    private void searchForGames() {
        gameList = steam.searchSuggestion(searchText);
        List<String> gameNames = new ArrayList<>();
        
        for (Game g : gameList) {
            gameNames.add(g.getName());
        }

        if (!gameNames.isEmpty()) {
            // To avoid IllegalStateException
            Platform.runLater(() -> {
                searchGame.getItems().clear();
                searchGame.getItems().addAll(gameNames);
                searchGame.show();
            });
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        state.setOnAction(this::onStateAction);
    }
}
