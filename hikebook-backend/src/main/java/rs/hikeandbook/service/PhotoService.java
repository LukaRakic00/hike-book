package rs.hikeandbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.hikeandbook.dto.ApiResponse;
import rs.hikeandbook.dto.PhotoDto;
import rs.hikeandbook.model.Photo;
import rs.hikeandbook.model.Trail;
import rs.hikeandbook.model.User;
import rs.hikeandbook.repository.PhotoRepository;
import rs.hikeandbook.repository.TrailRepository;
import rs.hikeandbook.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private TrailRepository trailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public ApiResponse<Page<PhotoDto>> getPhotosByTrailId(Integer trailId, Integer limit, Integer offset) {
        try {
            Pageable pageable = PageRequest.of(offset != null ? offset : 0, limit != null ? limit : 10);
            Page<Photo> photos = photoRepository.findByTrailId(trailId, pageable);
            Page<PhotoDto> photoDtos = photos.map(this::convertToDto);
            
            return ApiResponse.success(photoDtos, "Photos retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve photos: " + e.getMessage());
        }
    }

    public ApiResponse<List<PhotoDto>> getAllPhotosByTrailId(Integer trailId) {
        try {
            List<Photo> photos = photoRepository.findByTrailIdOrderByMainAndDate(trailId);
            List<PhotoDto> photoDtos = photos.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            
            return ApiResponse.success(photoDtos, "Photos retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve photos: " + e.getMessage());
        }
    }

    public ApiResponse<PhotoDto> getPhotoById(Integer id) {
        try {
            Optional<Photo> photo = photoRepository.findById(id);
            if (photo.isPresent()) {
                PhotoDto photoDto = convertToDto(photo.get());
                return ApiResponse.success(photoDto, "Photo retrieved successfully");
            } else {
                return ApiResponse.error("Photo not found");
            }
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve photo: " + e.getMessage());
        }
    }

    public ApiResponse<PhotoDto> createPhoto(Integer trailId, Integer userId, String url, String description, 
                                           BigDecimal latitude, BigDecimal longitude, Boolean isMain) {
        try {
            Optional<Trail> trail = trailRepository.findById(trailId);
            Optional<User> user = userRepository.findById(userId);

            if (!trail.isPresent()) {
                return ApiResponse.error("Trail not found");
            }

            if (!user.isPresent()) {
                return ApiResponse.error("User not found");
            }

            Photo photo = new Photo();
            photo.setTrail(trail.get());
            photo.setUser(user.get());
            photo.setUrl(url);
            photo.setDescription(description);
            photo.setLatitude(latitude);
            photo.setLongitude(longitude);
            photo.setIsMain(isMain != null ? isMain : false);
            photo.setDate(LocalDateTime.now());

            Photo savedPhoto = photoRepository.save(photo);
            PhotoDto photoDto = convertToDto(savedPhoto);

            return ApiResponse.success(photoDto, "Photo created successfully");
        } catch (Exception e) {
            return ApiResponse.error("Failed to create photo: " + e.getMessage());
        }
    }

    public ApiResponse<String> deletePhoto(Integer photoId, Integer userId) {
        try {
            Optional<Photo> photo = photoRepository.findById(photoId);
            
            if (!photo.isPresent()) {
                return ApiResponse.error("Photo not found");
            }

            // Check if user owns the photo or is admin (you can add admin check later)
            if (!photo.get().getUser().getId().equals(userId)) {
                return ApiResponse.error("You can only delete your own photos");
            }

            photoRepository.delete(photo.get());
            
            return ApiResponse.success("Photo deleted successfully", "Photo deleted successfully");
        } catch (Exception e) {
            return ApiResponse.error("Failed to delete photo: " + e.getMessage());
        }
    }

    public ApiResponse<PhotoDto> setMainPhoto(Integer photoId, Integer userId) {
        try {
            Optional<Photo> photo = photoRepository.findById(photoId);
            
            if (!photo.isPresent()) {
                return ApiResponse.error("Photo not found");
            }

            // Check if user owns the photo
            if (!photo.get().getUser().getId().equals(userId)) {
                return ApiResponse.error("You can only modify your own photos");
            }

            // Set all other photos of this trail to not main
            List<Photo> trailPhotos = photoRepository.findByTrailId(photo.get().getTrail().getId());
            for (Photo p : trailPhotos) {
                p.setIsMain(false);
                photoRepository.save(p);
            }

            // Set this photo as main
            photo.get().setIsMain(true);
            Photo savedPhoto = photoRepository.save(photo.get());
            PhotoDto photoDto = convertToDto(savedPhoto);

            return ApiResponse.success(photoDto, "Main photo set successfully");
        } catch (Exception e) {
            return ApiResponse.error("Failed to set main photo: " + e.getMessage());
        }
    }

    private PhotoDto convertToDto(Photo photo) {
        PhotoDto dto = new PhotoDto();
        dto.setId(photo.getId());
        dto.setTrailId(photo.getTrail().getId());
        dto.setUserId(photo.getUser().getId());
        dto.setUserEmail(photo.getUser().getEmail());
        dto.setUrl(photo.getUrl());
        dto.setDescription(photo.getDescription());
        dto.setDate(photo.getDate());
        dto.setLatitude(photo.getLatitude());
        dto.setLongitude(photo.getLongitude());
        dto.setIsMain(photo.getIsMain());
        dto.setCreatedAt(photo.getCreatedAt());
        return dto;
    }
} 