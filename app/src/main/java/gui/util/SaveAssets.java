package gui.util;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class SaveAssets {
    
    public static void saveImage(String url, String filename) {
        try {
            URL link = new URI(url).toURL();

            try (InputStream in = link.openStream()) {
                String path = SaveAssets.class.getResource("/gui/imgs/gameassets/").getPath()+filename+".jpg";
                System.out.println(path);
                Path assetsPath = Path.of(path);

                Files.copy(in, assetsPath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static String loadImage(String filename) {
        return SaveAssets.class.getResource("/gui/imgs/gameassets/").getPath()+filename+".jpg";
    }
}
