package twittrfx;

public class Bird {

    private String birdName;
    private String populationTrend;
    private String populationStatus;
    private String imageUrl;

    public Bird(String birdName, String populationTrend, String populationStatus, String imageUrl) {
        this.birdName = birdName;
        this.populationTrend = populationTrend;
        this.populationStatus = populationStatus;
        this.imageUrl = imageUrl;
    }

    public String getBirdName() {
        return birdName;
    }

    public void setBirdName(String birdName) {
        this.birdName = birdName;
    }

    public String getPopulationTrend() {
        return populationTrend;
    }

    public void setPopulationTrend(String populationTrend) {
        this.populationTrend = populationTrend;
    }

    public String getPopulationStatus() {
        return populationStatus;
    }

    public void setPopulationStatus(String populationStatus) {
        this.populationStatus = populationStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return birdName;
    }
}
