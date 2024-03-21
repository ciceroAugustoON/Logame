package gui;

import java.io.IOException;

import gui.scenes.GameListViewController;
import gui.scenes.GameViewController;
import gui.util.ViewUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public final class UniversalController {
    private static MainViewController mainViewController;
    private static AddGameViewController addGameViewController;
    private static GameListViewController gameListViewController;
    private static GameViewController gameViewController;

    public static MainViewController getMainViewController() {
        return mainViewController;
    }

    public static Parent loadMainView() throws IOException{
        FXMLLoader loader = ViewUtils.loadFXML("/gui/MainView.fxml");
        Parent parent = loader.load();
        mainViewController = loader.getController();

        return parent;
    }

    public static AddGameViewController getAddGameViewController() {
        return addGameViewController;
    }

    public static Parent loadAddGameView() {
        try {
            FXMLLoader loader = ViewUtils.loadFXML("/gui/AddGameView.fxml");
            Parent parent = loader.load();
            addGameViewController = loader.getController();

            return parent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }

    public static GameListViewController getGameListViewController() {
        return gameListViewController;
    }

    public static Parent loadGameListView(String state) {
        GameListViewController.setGameList(state);
        try {
            FXMLLoader loader = ViewUtils.loadFXML("/gui/scenes/GameListView.fxml");
            Parent parent = loader.load();
            gameListViewController = loader.getController();
            
            return parent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GameViewController getGameViewController() {
        return gameViewController;
    }
}
