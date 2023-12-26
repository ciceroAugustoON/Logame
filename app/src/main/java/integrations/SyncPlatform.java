package integrations;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SyncPlatform {

    public static boolean ifSyncFileExist() {
        File sync = new File(SyncPlatform.class.getResource("/integrations/").getPath()+"Sync.json");

        return sync.exists();
    }

    public static JsonNode getPlatformInfo(String platform) {
        File sync = new File(SyncPlatform.class.getResource("/integrations/").getPath()+"Sync.json");

        ObjectMapper om = new ObjectMapper();

        try {
            JsonNode syncNode = om.readTree(sync).get(platform);
            return syncNode;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
