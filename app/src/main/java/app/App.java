package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Logame");
            stage.show();
            // To ensure that all tasks are terminated.
            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
