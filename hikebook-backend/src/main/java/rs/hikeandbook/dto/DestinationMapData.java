package rs.hikeandbook.dto;

import java.util.List;

public class DestinationMapData {
    private Integer id;
    private String name;
    private String region;
    private Double latitude;
    private Double longitude;
    private Integer trailCount;
    private String imageUrl;
    private List<TrailMarkerData> trails;
    
    // Constructors
    public DestinationMapData() {}
    
    public DestinationMapData(Integer id, String name, String region, Double latitude, Double longitude, 
                             Integer trailCount, String imageUrl, List<TrailMarkerData> trails) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.trailCount = trailCount;
        this.imageUrl = imageUrl;
        this.trails = trails;
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
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
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
    
    public Integer getTrailCount() {
        return trailCount;
    }
    
    public void setTrailCount(Integer trailCount) {
        this.trailCount = trailCount;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public List<TrailMarkerData> getTrails() {
        return trails;
    }
    
    public void setTrails(List<TrailMarkerData> trails) {
        this.trails = trails;
    }
} 