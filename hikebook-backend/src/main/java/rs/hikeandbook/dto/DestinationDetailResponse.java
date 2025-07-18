package rs.hikeandbook.dto;

import java.util.List;

public class DestinationDetailResponse {
    private DestinationMapData destination;
    private List<TrailMarkerData> trails;
    
    // Constructors
    public DestinationDetailResponse() {}
    
    public DestinationDetailResponse(DestinationMapData destination, List<TrailMarkerData> trails) {
        this.destination = destination;
        this.trails = trails;
    }
    
    // Getters and Setters
    public DestinationMapData getDestination() {
        return destination;
    }
    
    public void setDestination(DestinationMapData destination) {
        this.destination = destination;
    }
    
    public List<TrailMarkerData> getTrails() {
        return trails;
    }
    
    public void setTrails(List<TrailMarkerData> trails) {
        this.trails = trails;
    }
} 