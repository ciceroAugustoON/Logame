package gui.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import db.GameDAO;
import entity.Game;
import gui.MainViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class GameList implements Initializable {

    @FXML
    private ScrollPane content;

    @FXML
    private TableView<Game> list;
    @FXML
    private TableColumn<Game, ImageView> icon;
    @FXML
    private TableColumn<Game, String> name;
    
    private static ScrollPane  scrollMain;
    private static ObservableList<Game> listGames;
    private static ImageView addGameButton; 
    
    @FXML
    private void onItemListClicked() {
        // Verifying the selected game
        Game g = list.selectionModelProperty().getValue().getSelectedItem();
        System.out.println(g.infoGame());
        // Verifying double click
        list.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2 && g != null) {
                        MainViewController.setGameView(g, scrollMain, addGameButton);
                    }
                }
            }
        });
    }

    public static void setConfList(String state, ScrollPane scroll, ImageView addGameButton) {
        listGames = FXCollections.observableList(GameDAO.selectData(state));
        scrollMain = scroll;
        GameList.addGameButton = addGameButton;
        System.out.println(state);
    }
    
    public ScrollPane getScrollPane() {
        return content;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        icon.setCellValueFactory(new PropertyValueFactory<Game, ImageView>("icon"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        icon.setCellFactory(column -> new TableCell<Game, ImageView>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setGraphic(item);
                } else {
                    setGraphic(null);
                }
            }
        });

        list.setItems(listGames);
    }
}
