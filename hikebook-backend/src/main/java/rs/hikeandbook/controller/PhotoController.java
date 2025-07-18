package rs.hikeandbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.hikeandbook.dto.ApiResponse;
import rs.hikeandbook.dto.PhotoDto;
import rs.hikeandbook.service.PhotoService;
import rs.hikeandbook.util.JwtUtil;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "*")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/trail/{trailId}")
    public ResponseEntity<ApiResponse<Page<PhotoDto>>> getPhotosByTrailId(
            @PathVariable Integer trailId,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset) {
        
        ApiResponse<Page<PhotoDto>> response = photoService.getPhotosByTrailId(trailId, limit, offset);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/trail/{trailId}/all")
    public ResponseEntity<ApiResponse<List<PhotoDto>>> getAllPhotosByTrailId(@PathVariable Integer trailId) {
        ApiResponse<List<PhotoDto>> response = photoService.getAllPhotosByTrailId(trailId);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PhotoDto>> getPhotoById(@PathVariable Integer id) {
        ApiResponse<PhotoDto> response = photoService.getPhotoById(id);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PhotoDto>> createPhoto(
            @RequestParam Integer trailId,
            @RequestParam String url,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal latitude,
            @RequestParam(required = false) BigDecimal longitude,
            @RequestParam(required = false) Boolean isMain,
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
        ApiResponse<PhotoDto> response = photoService.createPhoto(trailId, userId, url, description, latitude, longitude, isMain);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePhoto(
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
        ApiResponse<String> response = photoService.deletePhoto(id, userId);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}/main")
    public ResponseEntity<ApiResponse<PhotoDto>> setMainPhoto(
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
        ApiResponse<PhotoDto> response = photoService.setMainPhoto(id, userId);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
} 