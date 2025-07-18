package rs.hikeandbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.hikeandbook.dto.DestinationDetailResponse;
import rs.hikeandbook.dto.DestinationMapData;
import rs.hikeandbook.dto.ExploreResponse;
import rs.hikeandbook.dto.TrailMarkerData;
import rs.hikeandbook.model.Destination;
import rs.hikeandbook.model.Trail;
import rs.hikeandbook.repository.DestinationRepository;
import rs.hikeandbook.repository.TrailRepository;
import rs.hikeandbook.util.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DestinationService {
    
    @Autowired
    private DestinationRepository destinationRepository;
    
    @Autowired
    private TrailRepository trailRepository;
    
    public ExploreResponse getExploreData() {
        List<Object[]> destinationsWithCount = destinationRepository.findAllWithTrailCount();
        List<DestinationMapData> destinations = new ArrayList<>();
        
        for (Object[] result : destinationsWithCount) {
            Destination dest = (Destination) result[0];
            Long trailCount = (Long) result[1];
            
            DestinationMapData data = new DestinationMapData();
            data.setId(dest.getId());
            data.setName(dest.getName());
            data.setRegion(dest.getRegion());
            data.setLatitude(dest.getLatitude() != null ? dest.getLatitude().doubleValue() : null);
            data.setLongitude(dest.getLongitude() != null ? dest.getLongitude().doubleValue() : null);
            data.setImageUrl(dest.getImage());
            data.setTrailCount(trailCount != null ? trailCount.intValue() : 0);
            
            destinations.add(data);
        }
        
        ExploreResponse response = new ExploreResponse();
        response.setDestinations(destinations);
        response.setAvailableActivities(getAvailableActivities());
        response.setAvailableDifficulties(getAvailableDifficulties());
        
        return response;
    }
    
    public DestinationDetailResponse getDestinationDetail(Integer destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
            .orElseThrow(() -> new ResourceNotFoundException("Destination not found"));
        
        List<Trail> trails = trailRepository.findByDestinationId(destinationId);
        List<TrailMarkerData> trailMarkers = trails.stream()
            .map(this::convertToTrailMarkerData)
            .collect(Collectors.toList());
        
        DestinationDetailResponse response = new DestinationDetailResponse();
        response.setDestination(convertToDestinationData(destination));
        response.setTrails(trailMarkers);
        
        return response;
    }
    
    public List<TrailMarkerData> getDestinationTrailsWithFilters(Integer destinationId, Integer activityId, String difficulty) {
        List<Trail> trails = trailRepository.findByDestinationIdAndFilters(destinationId, activityId, difficulty);
        return trails.stream()
            .map(this::convertToTrailMarkerData)
            .collect(Collectors.toList());
    }
    
    private TrailMarkerData convertToTrailMarkerData(Trail trail) {
        TrailMarkerData data = new TrailMarkerData();
        data.setId(trail.getId());
        data.setName(trail.getName());
        data.setLatitude(trail.getCenterLatitude() != null ? trail.getCenterLatitude().doubleValue() : 
                        trail.getLatitude() != null ? trail.getLatitude().doubleValue() : null);
        data.setLongitude(trail.getCenterLongitude() != null ? trail.getCenterLongitude().doubleValue() : 
                         trail.getLongitude() != null ? trail.getLongitude().doubleValue() : null);
        data.setDifficulty(trail.getDifficulty());
        data.setLength(trail.getLength() != null ? trail.getLength().doubleValue() : null);
        data.setRating(trail.getRating() != null ? trail.getRating().doubleValue() : null);
        data.setMainImage(trail.getMainImage());
        
        // Dodaj aktivnosti
        if (trail.getActivities() != null) {
            List<String> activityNames = trail.getActivities().stream()
                .map(activity -> activity.getName())
                .collect(Collectors.toList());
            data.setActivities(activityNames);
        } else {
            data.setActivities(new ArrayList<>());
        }
        
        return data;
    }
    
    private DestinationMapData convertToDestinationData(Destination destination) {
        DestinationMapData data = new DestinationMapData();
        data.setId(destination.getId());
        data.setName(destination.getName());
        data.setRegion(destination.getRegion());
        data.setLatitude(destination.getLatitude() != null ? destination.getLatitude().doubleValue() : null);
        data.setLongitude(destination.getLongitude() != null ? destination.getLongitude().doubleValue() : null);
        data.setImageUrl(destination.getImage());
        data.setTrailCount(0); // Ovo će biti postavljeno kasnije ako je potrebno
        return data;
    }
    
    private List<String> getAvailableActivities() {
        // TODO: implementiraj dinamičko učitavanje iz baze
        return Arrays.asList("Hiking", "Biking", "Running", "Walking");
    }
    
    private List<String> getAvailableDifficulties() {
        return Arrays.asList("easy", "moderate", "hard");
    }
} 