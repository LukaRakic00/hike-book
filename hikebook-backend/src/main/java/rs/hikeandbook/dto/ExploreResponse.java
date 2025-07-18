package rs.hikeandbook.dto;

import java.util.List;

public class ExploreResponse {
    private List<DestinationMapData> destinations;
    private List<String> availableActivities;
    private List<String> availableDifficulties;
    
    // Constructors
    public ExploreResponse() {}
    
    public ExploreResponse(List<DestinationMapData> destinations, List<String> availableActivities, List<String> availableDifficulties) {
        this.destinations = destinations;
        this.availableActivities = availableActivities;
        this.availableDifficulties = availableDifficulties;
    }
    
    // Getters and Setters
    public List<DestinationMapData> getDestinations() {
        return destinations;
    }
    
    public void setDestinations(List<DestinationMapData> destinations) {
        this.destinations = destinations;
    }
    
    public List<String> getAvailableActivities() {
        return availableActivities;
    }
    
    public void setAvailableActivities(List<String> availableActivities) {
        this.availableActivities = availableActivities;
    }
    
    public List<String> getAvailableDifficulties() {
        return availableDifficulties;
    }
    
    public void setAvailableDifficulties(List<String> availableDifficulties) {
        this.availableDifficulties = availableDifficulties;
    }
} 