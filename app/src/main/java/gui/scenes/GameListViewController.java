package gui.scenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.App;
import db.GameDAO;
import entities.Game;
import gui.MainViewController;
import gui.UniversalController;
import gui.util.Assets;
import gui.util.ViewUtils;
import gui.util.enumerations.AssetType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class GameListViewController implements Initializable {

    @FXML
    private ScrollPane content;
    @FXML
    private TableView<Game> list;
    @FXML
    private TableColumn<Game, String> icon;
    @FXML
    private TableColumn<Game, String> name;

    private static ObservableList<Game> listGames;
    
    @FXML
    private void onItemListClicked() {
        if (!list.selectionModelProperty().getValue().isEmpty()) {
            // Verifying the selected game
            Game g = list.selectionModelProperty().getValue().getSelectedItem();
            // Verifying double click
            list.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2 && g != null) {
                            UniversalController.getMainViewController().loadGameView(g.getId());
                        }
                    }
                }
            });
        }

    }

    public static boolean setGameList(String state) {
        listGames = FXCollections.observableList(GameDAO.selectData(state));

        if (listGames == null) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        icon.setCellValueFactory(new PropertyValueFactory<>("icon"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        icon.setCellFactory(column -> new TableCell<Game, String>() {
            @Override
            protected void updateItem(String imagename, boolean empty) {
                super.updateItem(imagename, empty);
                if (imagename != null && !empty) {
                    Image img = new Image(Assets.loadImage(imagename, AssetType.ICON));
                    ImageView imgView = new ImageView(img);
                    imgView.setFitHeight(32);
                    imgView.setFitWidth(32);
                    setGraphic(imgView);
                } else {
                    setGraphic(null);
                }
            }
        });

        list.setItems(listGames);
    }
}
