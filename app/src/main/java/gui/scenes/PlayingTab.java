package gui.scenes;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import db.GameDAO;
import entity.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PlayingTab implements Initializable {

    @FXML
    private TableView<Game> tbPlaying;

    @FXML
    private TableColumn<Game, String> columnID = new TableColumn<>("ID");

    @FXML
    private TableColumn<Game, String> columnName = new TableColumn<>("Name:");

    @FXML
    private TableColumn<Game, String> columnPlatform = new TableColumn<>("Platform");

    private List<Game> listGames;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPlatform.setCellValueFactory(new PropertyValueFactory<>("platform"));
        listGames = GameDAO.selectData();
        List<TableColumn<Game, String>> columns = Arrays.asList(columnID, columnName, columnPlatform);
        tbPlaying.getColumns().addAll(columns);
        tbPlaying.getItems().addAll(listGames);
    }
}
