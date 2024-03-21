
package entities;

import java.time.LocalDate;

public class Instance {
    // general informations
    private String platform;
    private String state;
    // progression
    private String[] progress;
    // finished datas
    private LocalDate finishedDate;
    private int time;

    public Instance(String platform, String state) {
        this.platform = platform;
        this.state = state;
    }

    public Instance(String platform, String state, LocalDate finishedDate, int time) {
        this.platform = platform;
        this.state = state;
        this.finishedDate = finishedDate;
        this.time = time;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String[] getProgress() {
        return progress;
    }

    public void setProgress(String[] progress) {
        this.progress = progress;
    }

    public LocalDate getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(LocalDate finishedDate) {
        this.finishedDate = finishedDate;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
