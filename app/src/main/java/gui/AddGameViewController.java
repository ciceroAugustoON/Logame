package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.ViewUtils;
import integrations.SyncPlatform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class AddGameViewController implements Initializable{
    
    @FXML
    private ComboBox<String> platformComboBox;

    @FXML
    private Button selectPlatformButton;

    public static String PlatformSelected;

    @FXML
    public void onCbPlatformAction() {
        PlatformSelected = platformComboBox.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void onBtnSelectPlatformAction() {
        boolean syncValidate = false;
        switch (PlatformSelected) {
            case "Steam":
                syncValidate = SyncPlatform.ifSyncFileExist();
                break;
        
            default:
                break;
        }

        if (syncValidate) {
            ViewUtils.redrawScene("/gui/scenes/GameInfo.fxml");
        } else {
            Alerts.showAlert("Sync Error", null, "The synchronization is not configured.", AlertType.ERROR);
        }
        
    }

    public void cbPlatformLoad() {
        // Plataform list
        ObservableList<String> plataformGamesList = FXCollections.observableArrayList("Steam", "GOG", "PSX", "PS2");

        // Load combobox
        platformComboBox.setItems(plataformGamesList);
    }

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbPlatformLoad();
    }
}
