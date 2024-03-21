package gui;

import db.GameDAO;
import entities.Game;
import entities.Instance;
import gui.util.Alerts;
import gui.util.Assets;
import gui.util.Constraints;
import gui.util.enumerations.AssetType;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddGameViewController implements Initializable {

    // Required Fields
    @FXML
    private Label nameLabel;
    @FXML
    private TextField gameNameTxtField;
    @FXML
    private Label stateLabel;
    @FXML
    private ChoiceBox<String> gameStateChBox;
    @FXML
    private Label platformLabel;
    @FXML
    private ComboBox<String> platformCbBox;
    // Required if state = "Finished"
    @FXML
    private TextField hoursTxtField;
    @FXML
    private TextField minutesTxtField;
    @FXML
    private DatePicker datePicker;
    // Optional Fields
    @FXML
    private ComboBox<String> genreCbBox;
    @FXML
    private ComboBox<String> scopeCbBox;
    @FXML
    private TextField realeseYearTxtField;
    // Game Assets
    @FXML
    private ImageView coverImgView;
    @FXML
    private ImageView iconImgView;

    @FXML
    private Button addGameBtn;

    private final String empyCover = AddGameViewController.class.getResource("/gui/imgs/gameassets/cover_empty.png").toExternalForm();
    private final String emptyIcon = AddGameViewController.class.getResource("/gui/imgs/gameassets/icon_empty.png").toExternalForm();
    private String coverPath = null;
    private String iconPath = null;
    private static Stage stage;

    @FXML
    private void onCoverMouseClicked() {
        coverImgView.setImage(new Image(empyCover));
        Image coverImage = Assets.assetFileChooser(AssetType.COVER);
        if (coverImage != null) {
            coverImgView.setImage(coverImage);
        }
    }

    @FXML
    private void onIconMouseClicked() {
        iconImgView.setImage(new Image(emptyIcon));
        Image iconImage = Assets.assetFileChooser(AssetType.ICON);
        if (iconImage != null) {
            iconImgView.setImage(iconImage);
        }
    }

    @FXML
    private void onAddGameBtnAction() {
        coverPath = coverImgView.getImage().getUrl();
        System.out.println("-----VERIFICACAO--------");
        System.out.println(coverPath);
        System.out.println(empyCover.toString());

        if (coverPath.equals(empyCover.toString())) {
            coverPath = null;
        }
        iconPath = iconImgView.getImage().getUrl();
        if (iconPath.equals(emptyIcon.toString())) {
            iconPath = null;
        }

        if (gameNameTxtField.getText().isBlank() || gameStateChBox.getValue() == null || platformCbBox.getValue() == null) {

            String message = "You have requested fields not filled";

            nameLabel.setTextFill((gameNameTxtField.getText().isEmpty()) ? Color.RED : Color.BLACK);

            stateLabel.setTextFill((gameStateChBox.getValue() == null) ? Color.RED : Color.BLACK);

            platformLabel.setTextFill((platformCbBox.getValue() == null || platformCbBox.getValue().isBlank()) ? Color.RED : Color.BLACK);

            Alerts.showAlert("Requested Fields", null, message, Alert.AlertType.WARNING);

            return;
        }

        Game g = new Game(0, gameNameTxtField.getText());

        g.setRealese((!(realeseYearTxtField.getText() == null || realeseYearTxtField.getText().isBlank())) ? realeseYearTxtField.getText() : null);

        g.setGenre((!(genreCbBox.getValue() == null || genreCbBox.getValue().isBlank())) ? genreCbBox.getValue() : null);

        g.setScope((!(scopeCbBox.getValue() == null || scopeCbBox.getValue().isBlank())) ? scopeCbBox.getValue() : null);

        if (gameStateChBox.getValue().equals("Finished")) {
            int time = Integer.parseInt(hoursTxtField.getText()) * 60 + Integer.parseInt(minutesTxtField.getText());
            Instance i = new Instance(platformCbBox.getValue(), "Finished", datePicker.getValue(), time);
            GameDAO.insertData(g, i, coverPath, iconPath);
        } else {
            Instance i = new Instance(platformCbBox.getValue(), gameStateChBox.getValue());
            GameDAO.insertData(g, i, coverPath, iconPath);
        }

        Alerts.showAlert("Successful", null, "Game added to your list", Alert.AlertType.INFORMATION);
        stage.close();
    }

    public static void setStage(Stage stage) {
        AddGameViewController.stage = stage;
    }

    private void onOffElements(boolean setDisable, Node[] elements) {
        for (Node n : elements) {
            n.setDisable(setDisable);
        }
    }

    private void cleanTextFields(TextField[] txtFields) {
        for (TextField t : txtFields) {
            t.setText(null);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameStateChBox.getItems().addAll("Playing", "Next", "Backlog", "Finished");
        platformCbBox.getItems().addAll(Game.getPlatforms());

        Constraints.setTextFieldInteger(hoursTxtField);
        Constraints.setTextFieldInteger(minutesTxtField);
        Constraints.setTextFieldMaxLenght(minutesTxtField, 2);

        gameStateChBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Finished")) {
                onOffElements(false, new Node[]{datePicker, hoursTxtField, minutesTxtField});
            } else {
                onOffElements(true, new Node[]{datePicker, hoursTxtField, minutesTxtField});
                cleanTextFields(new TextField[]{hoursTxtField, minutesTxtField});
            }
        });
    }
}
