package gui.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import db.GameDAO;
import entity.Game;
import gui.MainViewController;
import gui.util.Assets;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class GameList implements Initializable {

    @FXML
    private ScrollPane content;

    @FXML
    private TableView<Game> list;
    @FXML
    private TableColumn<Game, String> icon;
    @FXML
    private TableColumn<Game, String> name;
    
    private static ScrollPane  scrollMain;
    private static ObservableList<Game> listGames;
    private static ImageView addGameButton; 
    
    @FXML
    private void onItemListClicked() {
        if (!list.selectionModelProperty().getValue().isEmpty()) {
            // Verifying the selected game
            Game g = list.selectionModelProperty().getValue().getSelectedItem();
            System.out.println(g.toString());
            // Verifying double click
            list.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2 && g != null) {
                            MainViewController.setGameView(g.getId(), scrollMain, addGameButton);
                        }
                    }
                }
            });
        }
        
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
        icon.setCellValueFactory(new PropertyValueFactory<>("icon"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        icon.setCellFactory(column -> new TableCell<Game, String>() {
            @Override
            protected void updateItem(String imagename, boolean empty) {
                super.updateItem(imagename, empty);
                if (imagename != null && !empty) {
                    System.out.println("imagename");
                    Image img = new Image(Assets.loadImage(imagename));
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
