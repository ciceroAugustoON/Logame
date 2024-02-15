package gui.util;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Assets {

    public static enum AssetType {
        COVER,
        ICON
    }

    public static Image assetFileChooser(AssetType assetType) {
        Stage coverChooser = new Stage();
        FileChooser fileChooser = new FileChooser();
        
        if (assetType == AssetType.COVER) {
            fileChooser.setTitle("Select cover image");
        } else {
            fileChooser.setTitle("Select icon image");
        }
        
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        try {
            File selectedFile = fileChooser.showOpenDialog(coverChooser);

            if (selectedFile != null) {
                return new Image(selectedFile.toURI().toString());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveImage(String url, String filename) {
        String format = url.substring(url.length() - 4);
        System.out.println(format);

        try {
            URL link = new URI(url).toURL();

            try (InputStream in = link.openStream()) {
                String path = Assets.class.getResource("/gui/imgs/gameassets/").getPath() + filename + format;
                System.out.println(path);
                Path assetsPath = Path.of(path);

                Files.copy(in, assetsPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream loadImage(String filename) {
        String jpg = "/gui/imgs/gameassets/" + filename + ".jpg";
        String png = "/gui/imgs/gameassets/" + filename + ".png";

        InputStream jpgUrl = Assets.class.getResourceAsStream(jpg);
        InputStream pngUrl = Assets.class.getResourceAsStream(png);

        if (jpgUrl != null) {
            System.out.println(jpgUrl.toString());
            return jpgUrl;
        } else if (pngUrl != null) {
            System.out.println(pngUrl.toString());
            return pngUrl;
        } else {
            System.out.println("Nada encontrado");
            return null;
        }
    }

}
