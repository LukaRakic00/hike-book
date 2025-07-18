package rs.hikeandbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.hikeandbook.dto.ApiResponse;
import rs.hikeandbook.dto.UploadResponse;
import rs.hikeandbook.service.CloudinaryService;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*")
public class UploadController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<UploadResponse>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Please select a file to upload"));
            }

            String imageUrl = cloudinaryService.uploadImage(file);
            UploadResponse uploadResponse = UploadResponse.success(imageUrl, null);
            
            return ResponseEntity.ok(ApiResponse.success(uploadResponse, "Image uploaded successfully"));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Failed to upload image: " + e.getMessage()));
        }
    }

    @PostMapping("/image/{folder}")
    public ResponseEntity<ApiResponse<UploadResponse>> uploadImageToFolder(
            @RequestParam("file") MultipartFile file,
            @PathVariable String folder) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Please select a file to upload"));
            }

            String imageUrl = cloudinaryService.uploadImage(file, folder);
            UploadResponse uploadResponse = UploadResponse.success(imageUrl, null);
            
            return ResponseEntity.ok(ApiResponse.success(uploadResponse, "Image uploaded successfully"));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Failed to upload image: " + e.getMessage()));
        }
    }
} 