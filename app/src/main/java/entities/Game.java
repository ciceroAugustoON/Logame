package entities;

import java.util.ArrayList;
import java.util.List;


public class Game {
    // requesteds
    private int id;
    private String name;
    private List<Instance> instances;
    // decription
    private String realese  = "Unknow";
    private String genre  = "Unknow";
    private String scope  = "Unknow";
    // assets
    private String icon;
    private String cover;
    
    public Game(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }
    
    public void addInstances(Instance instance) {
        if (instances == null) {
            instances = new ArrayList<>();
        }
        
        instances.add(instance);
    }
    
    public String getRelease() {
        return realese;
    }
    
    public void setRealese(String realese) {
        this.realese = realese;
    }
    
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
    
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    
    @Override
    public String toString() {
        return name;
    }
    // Preset list of platforms
    public static List<String> getPlatforms() {
        List<String> sony = List.of("PSX", "PS2", "PSP", "PS3", "PSVita", "PS4", "PS5");
        List<String> nintendo = List.of("NES", "SNES", "GB", "GBC", "N64", "GBA", "GameCube", "NDS", "3DS","Wii", "Wii U", "Switch");
        List<String> sega = List.of("Master System", "Sega Genesis", "Game Gear", "Sega Saturn", "DreamCast");
        List<String> microsoft = List.of("Xbox", "Xbox 360", "Xbox One", "Xbox Series");
        
        List<String> allPlatforms = new ArrayList<>();
        allPlatforms.add("PC");
        allPlatforms.addAll(sony);
        allPlatforms.addAll(nintendo);
        allPlatforms.addAll(sega);
        allPlatforms.addAll(microsoft);
        
        return allPlatforms;
    } 

}
