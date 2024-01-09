package gui.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewUtils {
    private static Stage stage;
    private static Scene scene;

    public synchronized static Stage createNewStage(String pathFXML) {
        stage = new Stage();
        try {
            Parent parent = FXMLLoader.load(ViewUtils.class.getResource(pathFXML));
            
            scene = new Scene(parent);
            stage.setTitle("Add Game");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return stage;
    }

    public static void redrawScene(String pathFXML) {
        try {
            Parent newContent = FXMLLoader.load(ViewUtils.class.getResource(pathFXML));
            scene = new Scene(newContent);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static FXMLLoader loadFXML(String pathFXML) {
        FXMLLoader content = new FXMLLoader(ViewUtils.class.getResource(pathFXML));
        return content;
        
    }
}
