package rs.hikeandbook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trail")
public class Trail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private Integer id;
    
    @NotBlank
    @Size(max = 255)
    private String name;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;
    
    @NotNull
    @Column(precision = 5, scale = 2)
    private BigDecimal length; // in kilometers
    
    @NotNull
    @Column(name = "elevation_gain")
    private Integer elevationGain; // in meters
    
    @NotNull
    @Column(name = "estimated_time")
    private Integer estimatedTime; // in minutes
    
    @NotBlank
    @Size(max = 20)
    @Column(name = "trail_type")
    private String trailType; // 'loop', 'linear', 'out-and-back'
    
    @NotBlank
    @Size(max = 20)
    private String difficulty; // 'easy', 'moderate', 'hard'
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;
    
    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;
    
    @Column(name = "coordinates", columnDefinition = "TEXT")
    private String coordinates; // JSON array koordinata za rutu
    
    @Column(name = "center_latitude", precision = 10, scale = 8)
    private BigDecimal centerLatitude;
    
    @Column(name = "center_longitude", precision = 11, scale = 8)
    private BigDecimal centerLongitude;
    
    @Size(max = 500)
    @Column(name = "main_image")
    private String mainImage;
    
    @Column(precision = 3, scale = 2)
    private BigDecimal rating = BigDecimal.ZERO;
    
    @Column(name = "review_count")
    private Integer reviewCount = 0;
    
    @Size(max = 100)
    @Column(name = "best_time")
    private String bestTime;
    
    @Column(name = "dogs_allowed")
    private Boolean dogsAllowed = false;
    
    private Boolean active = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToMany
    @JoinTable(
        name = "trail_activity",
        joinColumns = @JoinColumn(name = "trail_id"),
        inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<Activity> activities;
    
    // Constructors
    public Trail() {}
    
    public Trail(String name, Destination destination, BigDecimal length, Integer elevationGain, 
                 Integer estimatedTime, String trailType, String difficulty) {
        this.name = name;
        this.destination = destination;
        this.length = length;
        this.elevationGain = elevationGain;
        this.estimatedTime = estimatedTime;
        this.trailType = trailType;
        this.difficulty = difficulty;
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
    
    public Destination getDestination() {
        return destination;
    }
    
    public void setDestination(Destination destination) {
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
    
    public String getCoordinates() {
        return coordinates;
    }
    
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
    
    public BigDecimal getCenterLatitude() {
        return centerLatitude;
    }
    
    public void setCenterLatitude(BigDecimal centerLatitude) {
        this.centerLatitude = centerLatitude;
    }
    
    public BigDecimal getCenterLongitude() {
        return centerLongitude;
    }
    
    public void setCenterLongitude(BigDecimal centerLongitude) {
        this.centerLongitude = centerLongitude;
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
    
    public List<Activity> getActivities() {
        return activities;
    }
    
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 