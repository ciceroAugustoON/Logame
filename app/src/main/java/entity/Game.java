package entity;

public class Game {
    
    private String id;
    private String name;
    private String platform;
    private String icon;
    private String state;

    public Game(String id, String name, String platform) {
        this.id = id;
        this.name = name;
        this.platform = platform;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String toString() {
        return name;
    }

    public String infoGame() {
        return "Id: "+id+" Name: "+name+" Platform: "+platform;
    }
}
