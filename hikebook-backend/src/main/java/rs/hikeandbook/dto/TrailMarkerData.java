package rs.hikeandbook.dto;

import java.util.List;

public class TrailMarkerData {
    private Integer id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String difficulty;
    private Double length;
    private Double rating;
    private String mainImage;
    private List<String> activities;
    
    // Constructors
    public TrailMarkerData() {}
    
    public TrailMarkerData(Integer id, String name, Double latitude, Double longitude, String difficulty, 
                          Double length, Double rating, String mainImage, List<String> activities) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.difficulty = difficulty;
        this.length = length;
        this.rating = rating;
        this.mainImage = mainImage;
        this.activities = activities;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public Double getLength() {
        return length;
    }
    
    public void setLength(Double length) {
        this.length = length;
    }
    
    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    public String getMainImage() {
        return mainImage;
    }
    
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
    
    public List<String> getActivities() {
        return activities;
    }
    
    public void setActivities(List<String> activities) {
        this.activities = activities;
    }
} 