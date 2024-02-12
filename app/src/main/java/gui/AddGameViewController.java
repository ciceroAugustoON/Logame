package gui;

import db.GameDAO;
import entity.Game;
import entity.Instance;
import gui.util.Alerts;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddGameViewController implements Initializable{
    // Required Fields
    @FXML
    private Label nameLabel;
    @FXML
    private TextField gameName;
    @FXML
    private Label stateLabel;
    @FXML
    private ChoiceBox<String> gameState;
    @FXML
    private Label platformLabel;
    @FXML
    private ComboBox<String> platformComboBox;
    
    // Required if state = "Finished"
    @FXML
    private TextField hours;
    
    @FXML
    private TextField minutes;
    
    @FXML
    private DatePicker date;
    // Optional Fields
    @FXML
    private ComboBox<String> genre;
    @FXML
    private ComboBox<String> scope;
    
    @FXML
    private TextField realeseYear;
    
    // Game Assets
    @FXML
    private ImageView cover;
    @FXML
    private ImageView icon;
    
    @FXML
    private Button addGameButton;
    
    private static Stage stage;
    
    @FXML
    private void onAddGameButtonAction() {
        if (gameName.getText().isBlank()|| gameState.getValue() == null || platformComboBox.getValue() == null) {
            String message = "You have requested fields not filled:";
            if (gameName.getText().isEmpty()) {
                nameLabel.setTextFill(Color.RED);
                message += "\nName";
            } else {
                nameLabel.setTextFill(Color.BLACK);
            }
            if (gameState.getValue() == null) {
                stateLabel.setTextFill(Color.RED);
                message += "\nState";
            } else {
                stateLabel.setTextFill(Color.BLACK);
            }
            if (platformComboBox.getValue() == null || platformComboBox.getValue().isBlank()) {
                platformLabel.setTextFill(Color.RED);
                message += "\nPlatform";
            } else {
               platformLabel.setTextFill(Color.BLACK);
            }
            Alerts.showAlert("Requested Fields", null, message, Alert.AlertType.WARNING);
        } else {
            Game g = new Game(0, gameName.getText());
            if (!(realeseYear.getText() == null || realeseYear.getText().isBlank())) {
                g.setRealese(realeseYear.getText());
            }
            if (!(genre.getValue() == null || genre.getValue().isBlank())) {
                g.setGenre(genre.getValue());
            }
            if (!(scope.getValue() == null || scope.getValue().isBlank())) {
                g.setScope(scope.getValue());
            }
            
            if (gameState.getValue().equals("Finished")) {
                int time = Integer.parseInt(hours.getText()) * 60 + Integer.parseInt(minutes.getText());
                Instance i = new Instance(platformComboBox.getValue(), "Finished", date.getValue(), time);
                GameDAO.insertData(g, i);
            } else {
                Instance i = new Instance(platformComboBox.getValue(), gameState.getValue());
                GameDAO.insertData(g, i);
            }
            Alerts.showAlert("Successful", null, "Game added to your list", Alert.AlertType.INFORMATION);
            stage.close();
        }
        
    }
    
    public static void setStage(Stage stage) {
        AddGameViewController.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameState.getItems().addAll("Playing", "Next", "Backlog", "Finished");
        platformComboBox.getItems().addAll(Game.getPlatforms());
        
        hours.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;
        }));
        minutes.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;
        }));
        
        gameState.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Finished")) {
               date.setDisable(false);
               hours.setDisable(false);
               minutes.setDisable(false);
            } else {
                date.setValue(null);
                date.setDisable(true);
                hours.setText(null);
                hours.setDisable(true);
                minutes.setText(null);
                minutes.setDisable(true);
            }
         });
    }
}
