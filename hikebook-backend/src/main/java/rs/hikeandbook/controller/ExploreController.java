package rs.hikeandbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.hikeandbook.dto.DestinationDetailResponse;
import rs.hikeandbook.dto.ExploreResponse;
import rs.hikeandbook.dto.TrailMarkerData;
import rs.hikeandbook.service.DestinationService;
import rs.hikeandbook.util.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/explore")
@CrossOrigin(origins = "*")
public class ExploreController {
    
    @Autowired
    private DestinationService destinationService;
    
    @GetMapping
    public ResponseEntity<ExploreResponse> getExploreData() {
        try {
            ExploreResponse response = destinationService.getExploreData();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
    
    @GetMapping("/{destinationId}")
    public ResponseEntity<DestinationDetailResponse> getDestinationDetail(
            @PathVariable Integer destinationId) {
        try {
            DestinationDetailResponse response = destinationService.getDestinationDetail(destinationId);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
    
    @GetMapping("/{destinationId}/trails")
    public ResponseEntity<List<TrailMarkerData>> getDestinationTrails(
            @PathVariable Integer destinationId,
            @RequestParam(required = false) Integer activityId,
            @RequestParam(required = false) String difficulty) {
        try {
            List<TrailMarkerData> trails = destinationService.getDestinationTrailsWithFilters(
                destinationId, activityId, difficulty);
            return ResponseEntity.ok(trails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
} 