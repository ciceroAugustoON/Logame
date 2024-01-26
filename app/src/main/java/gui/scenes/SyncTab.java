package gui.scenes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gui.util.Alerts;
import integrations.Steam;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SyncTab implements Initializable{

    @FXML
    private ScrollPane content; 
    
    @FXML
    private ImageView editContent;

    // Steam 
    @FXML
    private TextField steamApiKey;
    @FXML
    private TextField steamId;

    @FXML
    private ImageView steamApiKeyVerify;
    @FXML
    private ImageView steamIdVerify;

    // Save data
    @FXML
    private Button saveButton;

    private boolean toSaveApiKey = false;
    private boolean toSaveUserId = false;
    
    private ScheduledExecutorService schedule;
    private ScheduledExecutorService schedule2;
    
    @FXML
    private void onEditContentAction() {
        editMode(true);
    }

    @FXML
    private void onSteamApiKeyReleased() {
        // To avoid lag
        schedule.schedule(() -> {
            if (!steamApiKey.getText().isEmpty()) {
                isSteamApiKeyValid();
            }
        }, 2, TimeUnit.SECONDS);

    }

    @FXML
    private void onSteamIdReleased() {
        
        schedule2.schedule(() -> {
            if (!steamId.getText().isEmpty()) {
                isUserIdValid();
            }
        }, 2, TimeUnit.SECONDS);
        
    }

    @FXML
    private void onSaveButtonAction() {
        // Checks if the file exists and create it if not
        String pathFile = getClass().getResource("/integrations/").getPath();
        
        File syncFile = new File((pathFile + "/Sync.json"));
        if (!syncFile.exists()) {
            try {
                if (syncFile.createNewFile()) {
                    System.out.println("Arquivo criado.");
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Get data
        Steam syncSteam = new Steam(steamApiKey.getText(), steamId.getText());

        ObjectMapper om = new ObjectMapper();
        
        Map<String, Object> platforms = new HashMap<>();
        platforms.put("Steam", syncSteam);

        try {
            String jsonString = om.writeValueAsString(platforms);
            JsonNode node = om.readTree(jsonString);
            om.writeValue(syncFile, node);
            Alerts.showAlert("Sync", null, "Dados salvos com sucesso!", AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        editMode(false);
        
    }

    public void isSteamApiKeyValid() {
        InputStream imagePath;
        Image img;
        boolean isValid = Steam.apiKeyValidate(steamApiKey.getText());
        toSaveApiKey = isValid;
        if (isValid) {
            imagePath = getClass().getResourceAsStream("/gui/imgs/icons/check-solid.png");
            img = new Image(imagePath);
            steamId.setDisable(false);
            schedule2 = Executors.newScheduledThreadPool(1);
        } else {
            imagePath = getClass().getResourceAsStream("/gui/icons/imgs/xmark-solid.png");
            img = new Image(imagePath);
            steamId.setDisable(true);
        }
        enableSaveButton();
        steamApiKeyVerify.setImage(img);
    }
    public void isUserIdValid() {
        InputStream imagePath;
        Image img;

        boolean isValid = Steam.userIDValidate(steamApiKey.getText(), steamId.getText());
        toSaveUserId = isValid;
        if (isValid) {
            imagePath = getClass().getResourceAsStream("/gui/imgs/icons/check-solid.png");
            img = new Image(imagePath);
        } else {
            imagePath = getClass().getResourceAsStream("/gui/imgs/icons/xmark-solid.png");
            img = new Image(imagePath);
        }
        enableSaveButton();
        steamIdVerify.setImage(img);
    }
    
    private void editMode(boolean editMode) {
         if (editMode) {
            editContent.setVisible(!editMode);
            steamApiKey.setDisable(!editMode);
            steamId.setDisable(!editMode);
            steamApiKeyVerify.setVisible(editMode);
            steamIdVerify.setVisible(editMode);
       } else {
            editContent.setVisible(!editMode);
            steamApiKey.setDisable(!editMode);
            steamId.setDisable(!editMode);
            steamApiKeyVerify.setVisible(editMode);
            steamIdVerify.setVisible(editMode);
            saveButton.setDisable(!editMode);
        }
    }
    
    private void enableSaveButton() {
        if (toSaveApiKey && toSaveUserId) {
            saveButton.setDisable(false);
        } else {
            saveButton.setDisable(true);
        }
    }

    public ScrollPane getScrollPane() {
        return content;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schedule = Executors.newScheduledThreadPool(1);
        // Verify if sync file exists
        URL file = getClass().getResource("/integrations/Sync.json");
        if (file != null) {
            editMode(false);
        } else {
            editContent.setVisible(false);
        }
    }
}
