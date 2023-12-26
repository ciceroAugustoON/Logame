package gui.scenes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import integrations.Steam;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SyncTab implements Initializable{
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

    private long lasttime = -2000;

    @FXML
    private void onSteamApiKeyReleased() {
        long time = System.currentTimeMillis();
        InputStream imagePath;
        Image img;
        // To avoid lag
        System.out.println("Entrou, tempo: "+(time-lasttime));
        if ((time - lasttime) > 2000) {
            boolean isValid = Steam.apiKeyValidate(steamApiKey.getText());
            if (isValid) {
                imagePath = getClass().getResourceAsStream("/gui/imgs/check-solid.png");
                img = new Image(imagePath);
                steamId.setDisable(false);
            } else {
                imagePath = getClass().getResourceAsStream("/gui/imgs/xmark-solid.png");
                img = new Image(imagePath);
                steamId.setDisable(true);
            }
            steamApiKeyVerify.setImage(img);
            lasttime = time;
        }
    }

    @FXML
    private void onSteamIdReleased() {
        long time = System.currentTimeMillis();
        InputStream imagePath;
        Image img;

        if ((time - lasttime) > 2000) {
            boolean isValid = Steam.userIDValidate(steamApiKey.getText(), steamId.getText());
            if (isValid) {
                imagePath = getClass().getResourceAsStream("/gui/imgs/check-solid.png");
                img = new Image(imagePath);
            } else {
                imagePath = getClass().getResourceAsStream("/gui/imgs/xmark-solid.png");
                img = new Image(imagePath);
            }
            steamIdVerify.setImage(img);
            lasttime = time;
        }
    }

    @FXML
    private void onSaveButtonAction() {
        // Checks if the file exists
        String pathFile = getClass().getResource("/integrations/").getPath();

        System.out.println(pathFile);

        File syncFile = new File(pathFile+"Sync.json");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
