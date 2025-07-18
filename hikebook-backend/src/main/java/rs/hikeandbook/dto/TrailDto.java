package rs.hikeandbook.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TrailDto {
    
    private Integer id;
    private String name;
    private DestinationDto destination;
    private BigDecimal length;
    private Integer elevationGain;
    private Integer estimatedTime;
    private String trailType;
    private String difficulty;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String mainImage;
    private BigDecimal rating;
    private Integer reviewCount;
    private String bestTime;
    private Boolean dogsAllowed;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ActivityDto> activities;
    
    // Constructors
    public TrailDto() {}
    
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
    
    public DestinationDto getDestination() {
        return destination;
    }
    
    public void setDestination(DestinationDto destination) {
        this.destination = destination;
    }
    
    public BigDecimal getLength() {
        return length;
    }
    
    public void setLength(BigDecimal length) {
        this.length = length;
    }
    
    public Integer getElevationGain() {
        return elevationGain;
    }
    
    public void setElevationGain(Integer elevationGain) {
        this.elevationGain = elevationGain;
    }
    
    public Integer getEstimatedTime() {
        return estimatedTime;
    }
    
    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    
    public String getTrailType() {
        return trailType;
    }
    
    public void setTrailType(String trailType) {
        this.trailType = trailType;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    public String getMainImage() {
        return mainImage;
    }
    
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
    
    public BigDecimal getRating() {
        return rating;
    }
    
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
    
    public Integer getReviewCount() {
        return reviewCount;
    }
    
    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }
    
    public String getBestTime() {
        return bestTime;
    }
    
    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }
    
    public Boolean getDogsAllowed() {
        return dogsAllowed;
    }
    
    public void setDogsAllowed(Boolean dogsAllowed) {
        this.dogsAllowed = dogsAllowed;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<ActivityDto> getActivities() {
        return activities;
    }
    
    public void setActivities(List<ActivityDto> activities) {
        this.activities = activities;
    }
} 