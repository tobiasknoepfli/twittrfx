package twittrfx;

public class Bird {
    private String name;
    private String image;
    private String shortDescription;
    private String populationSize;
    private String maximumLifeSpanInYears;
    private String topSpeedInKmh;
    private String weight;
    private String length;
    private String wingspan;
    private String continents;
    private String diet;
    private String seasonalBehavior;
    private String independentAge;
    private String populationTrend;
    private String populationStatus;
    private String incubationPeriod;

    public Bird(String name, String image, String shortDescription, String populationSize, String maximumLifeSpanInYears,
                String topSpeedInKmh, String weight, String length, String wingspan, String continents,
                String diet, String seasonalBehavior, String independentAge, String populationTrend,
                String populationStatus, String incubationPeriod) {
        this.name = name;
        this.image = image;
        this.shortDescription = shortDescription;
        this.populationSize = populationSize;
        this.maximumLifeSpanInYears = maximumLifeSpanInYears;
        this.topSpeedInKmh = topSpeedInKmh;
        this.weight = weight;
        this.length = length;
        this.wingspan = wingspan;
        this.continents = continents;
        this.diet = diet;
        this.seasonalBehavior = seasonalBehavior;
        this.independentAge = independentAge;
        this.populationTrend = populationTrend;
        this.populationStatus = populationStatus;
        this.incubationPeriod = incubationPeriod;
    }

    // Add getters and setters for each property

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(String populationSize) {
        this.populationSize = populationSize;
    }

    public String getMaximumLifeSpanInYears() {
        return maximumLifeSpanInYears;
    }

    public void setMaximumLifeSpanInYears(String maximumLifeSpanInYears) {
        this.maximumLifeSpanInYears = maximumLifeSpanInYears;
    }

    public String getTopSpeedInKmh() {
        return topSpeedInKmh;
    }

    public void setTopSpeedInKmh(String topSpeedInKmh) {
        this.topSpeedInKmh = topSpeedInKmh;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWingspan() {
        return wingspan;
    }

    public void setWingspan(String wingspan) {
        this.wingspan = wingspan;
    }

    public String getContinents() {
        return continents;
    }

    public void setContinents(String continents) {
        this.continents = continents;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getSeasonalBehavior() {
        return seasonalBehavior;
    }

    public void setSeasonalBehavior(String seasonalBehavior) {
        this.seasonalBehavior = seasonalBehavior;
    }

    public String getIndependentAge() {
        return independentAge;
    }

    public void setIndependentAge(String independentAge) {
        this.independentAge = independentAge;
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

    public String getIncubationPeriod() {
        return incubationPeriod;
    }

    public void setIncubationPeriod(String incubationPeriod) {
        this.incubationPeriod = incubationPeriod;
    }
}
