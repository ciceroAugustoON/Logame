package integrations;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Game;

public class Steam {
    private String apiKey;
    private String userID;

    public Steam(String apiKey, String userID) {
        this.apiKey = apiKey;
        this.userID = userID;
    }

    public static enum AssetType {
        CAPSULE,
        HERO,
        LOGO
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getUserID() {
        return userID;
    }
    
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Game> searchSuggestion(String search) {
        List<Game> suggestions = new ArrayList<>();
        JsonNode consult = getGameList(true).get("response").get("games");
        
        for (JsonNode game : consult) {
            if (suggestions.size() < 5 && game.get("name").asText().toLowerCase().contains(search.toLowerCase())) {
                Game g = new Game(game.get("appid").asText(), game.get("name").asText(), "Steam");
                g.setIcon(game.get("img_icon_url").asText());
                suggestions.add(g);
            }
        }

        return suggestions;
    }

    public JsonNode getGameList(boolean include_appinfo) {
        JsonNode content = null;
        try {
            ObjectMapper om = new ObjectMapper();

            URL url = new URI("https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/?key="+apiKey+"&steamid="+userID+"&include_appinfo="+include_appinfo).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            content = om.readTree(conn.getInputStream());
            conn.disconnect();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String getGameAsset(String appid, AssetType assetType) {
        switch (assetType) {
            case CAPSULE:
                return "https://cdn.cloudflare.steamstatic.com/steam/apps/"+appid+"/library_600x900.jpg";
            case HERO:
                return "https://cdn.cloudflare.steamstatic.com/steam/apps/"+appid+"/library_hero.jpg";
            case LOGO:
                return "https://cdn.cloudflare.steamstatic.com/steam/apps/"+appid+"/logo.png";
            default:
                return "";
        }
        
    }

    public static String getIcon(String appid, String icon_url) {
        return "http://media.steampowered.com/steamcommunity/public/images/apps/"+appid+"/"+icon_url+".jpg";
    }

    public static boolean apiKeyValidate(String apiKey) {
        ObjectMapper om = new ObjectMapper();
        try {
            URL request = new URI("https://api.steampowered.com/IStoreService/GetAppList/v1/?key="+apiKey+"&max_results=1").toURL();
            HttpURLConnection conn = (HttpURLConnection) request.openConnection();

            JsonNode node = om.readTree(conn.getInputStream());
            if (node.has("response")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean userIDValidate(String apiKey, String userId) {
        ObjectMapper om = new ObjectMapper();
        try {
            URL request = new URI("https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/?key="+apiKey+"&steamids="+userId).toURL();
            HttpURLConnection conn = (HttpURLConnection) request.openConnection();

            JsonNode node = om.readTree(conn.getInputStream());
            JsonNode datas = node.get("response").get("players");

            if (datas.isArray() && datas.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
