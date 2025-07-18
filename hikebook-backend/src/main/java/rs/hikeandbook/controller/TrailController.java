package rs.hikeandbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.hikeandbook.dto.ApiResponse;
import rs.hikeandbook.dto.TrailDto;
import rs.hikeandbook.service.TrailService;
import rs.hikeandbook.util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/api/trails")
@CrossOrigin(origins = "*")
public class TrailController {
    
    @Autowired
    private TrailService trailService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<TrailDto>>> getTrails(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "difficulty", required = false) String difficulty,
            @RequestParam(value = "activity_id", required = false) Integer activityId,
            @RequestParam(value = "destination_id", required = false) Integer destinationId) {
        
        ApiResponse<Page<TrailDto>> response = trailService.getTrails(limit, offset, difficulty, activityId, destinationId);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TrailDto>> getTrailById(@PathVariable Integer id) {
        ApiResponse<TrailDto> response = trailService.getTrailById(id);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/favorites")
    public ResponseEntity<ApiResponse<List<TrailDto>>> getUserFavoriteTrails(
            @RequestHeader(value = "Authorization", required = false) String token) {
        
        if (token == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Authorization header is required"));
        }
        
        String actualToken = jwtUtil.extractTokenFromHeader(token);
        if (actualToken == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid token format"));
        }
        
        if (!jwtUtil.validateToken(actualToken)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid or expired token"));
        }
        
        Integer userId = jwtUtil.extractUserId(actualToken);
        ApiResponse<List<TrailDto>> response = trailService.getUserFavoriteTrails(userId);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/{id}/favorite")
    public ResponseEntity<ApiResponse<String>> addToFavorites(
            @PathVariable Integer id,
            @RequestHeader(value = "Authorization", required = false) String token) {
        
        if (token == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Authorization header is required"));
        }
        
        String actualToken = jwtUtil.extractTokenFromHeader(token);
        if (actualToken == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid token format"));
        }
        
        if (!jwtUtil.validateToken(actualToken)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid or expired token"));
        }
        
        Integer userId = jwtUtil.extractUserId(actualToken);
        ApiResponse<String> response = trailService.addToFavorites(userId, id);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/{id}/favorite")
    public ResponseEntity<ApiResponse<String>> removeFromFavorites(
            @PathVariable Integer id,
            @RequestHeader(value = "Authorization", required = false) String token) {
        
        if (token == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Authorization header is required"));
        }
        
        String actualToken = jwtUtil.extractTokenFromHeader(token);
        if (actualToken == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid token format"));
        }
        
        if (!jwtUtil.validateToken(actualToken)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid or expired token"));
        }
        
        Integer userId = jwtUtil.extractUserId(actualToken);
        ApiResponse<String> response = trailService.removeFromFavorites(userId, id);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
} 