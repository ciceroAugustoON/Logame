package gui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import db.GameDAO;
import entity.Game;
import gui.scenes.GameInfoController;
import gui.util.ViewUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button addGame;

    @FXML
    private Button conf;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Parent addGameParent;

    private int scrollVerify = -1;

    @FXML
    private void onBtnPlayingAction() {
        if (scrollVerify != 0) {
            scroll.setContent(ViewUtils.loadFXML("/gui/scenes/PlayingTab.fxml"));
            scrollVerify = 0;
        }
        
    }

    @FXML
    private void onBtnNextToPlayAction() {
        System.out.println("Próximos");
    }

    @FXML
    private void onBtnBacklogAction() {
        System.out.println("Pendentes");
    }

    @FXML
    private void onBtnCompletedAction() {
        System.out.println("Finalizados");
    }

    @FXML
    private void onBtnAddGameAction() {
        Stage stage = ViewUtils.createNewStage("/gui/AddGameView.fxml");
        GameInfoController.setStage(stage);
        // Steam.setUserID(SyncPlataform.getUserID());
    }

    @FXML
    private void onBtnConfAction() {
        if (scrollVerify != 4) {
            scrollVerify = 4;
            scroll.setContent(ViewUtils.loadFXML("/gui/scenes/SyncTab.fxml"));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
		
    }
}
