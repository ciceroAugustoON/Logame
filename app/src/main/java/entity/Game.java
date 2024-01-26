package entity;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Game {

    private String id;
    private String name;
    private String platform;
    private String icon;
    private String state;
    // decription
    private String publisher;
    private String developer;
    private String realese;
    private String genre;
    private String scope;

    public Game(String id, String name, String platform) {
        this.id = id;
        this.name = name;
        this.platform = platform;
        // null to Unknow
        publisher = "Unknow";
        developer = "Unknow";
        realese = "Unknow";
        genre = "Unknow";
        scope = "Unknow";
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

    public ImageView getIcon() {
        ImageView img = new ImageView(new Image(new File(icon).toURI().toString()));

        return img;
    }

    public String getIconURL() {
        return icon;
    }

    public void setIcon(String imagePath) {
        this.icon = imagePath;
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
        return "Id: " + id + " Name: " + name + " Platform: " + platform;
    }

    public void setDesc(String publisher, String developer, String realese, String genre, String scope) {
        this.publisher = publisher;
        this.developer = developer;
        this.realese = realese;
        this.genre = genre;
        this.scope = scope;
    }
    
    public String descGame() {
        return "Publisher: " + publisher + "\nDeveloper: " + developer + "\nRealese Date: " + realese + "\nGenre: " + genre + "\nScope: " + scope;
    }
}
