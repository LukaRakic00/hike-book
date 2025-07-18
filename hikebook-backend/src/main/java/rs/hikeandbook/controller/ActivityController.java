package rs.hikeandbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.hikeandbook.dto.ApiResponse;
import rs.hikeandbook.service.ActivityService;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityController {
    
    @Autowired
    private ActivityService activityService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<rs.hikeandbook.dto.ActivityDto>>> getAllActivities() {
        ApiResponse<List<rs.hikeandbook.dto.ActivityDto>> response = activityService.getAllActivities();
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
} 