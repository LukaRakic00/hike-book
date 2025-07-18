package rs.hikeandbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.hikeandbook.dto.ApiResponse;
import rs.hikeandbook.dto.TrailDto;
import rs.hikeandbook.model.FavoriteTrail;
import rs.hikeandbook.model.Trail;
import rs.hikeandbook.model.User;
import rs.hikeandbook.repository.FavoriteTrailRepository;
import rs.hikeandbook.repository.TrailRepository;
import rs.hikeandbook.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrailService {
    
    @Autowired
    private TrailRepository trailRepository;
    
    @Autowired
    private FavoriteTrailRepository favoriteTrailRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public ApiResponse<Page<TrailDto>> getTrails(Integer limit, Integer offset, String difficulty, 
                                                 Integer activityId, Integer destinationId) {
        try {
            Pageable pageable = PageRequest.of(offset != null ? offset : 0, limit != null ? limit : 10);
            Page<Trail> trails;
            
            if (difficulty != null && destinationId != null && activityId != null) {
                trails = trailRepository.findByDifficultyAndDestinationIdAndActivityId(difficulty, destinationId, activityId, pageable);
            } else if (difficulty != null && destinationId != null) {
                trails = trailRepository.findByDifficultyAndDestinationId(difficulty, destinationId, pageable);
            } else if (difficulty != null && activityId != null) {
                trails = trailRepository.findByDifficultyAndActivityId(difficulty, activityId, pageable);
            } else if (destinationId != null && activityId != null) {
                trails = trailRepository.findByDestinationIdAndActivityId(destinationId, activityId, pageable);
            } else if (difficulty != null) {
                trails = trailRepository.findByDifficulty(difficulty, pageable);
            } else if (destinationId != null) {
                trails = trailRepository.findByDestinationId(destinationId, pageable);
            } else if (activityId != null) {
                trails = trailRepository.findByActivityId(activityId, pageable);
            } else {
                trails = trailRepository.findAllActive(pageable);
            }
            
            Page<TrailDto> trailDtos = trails.map(this::convertToDto);
            return ApiResponse.success(trailDtos, "Trails retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve trails: " + e.getMessage());
        }
    }
    
    public ApiResponse<TrailDto> getTrailById(Integer id) {
        try {
            Optional<Trail> trail = trailRepository.findById(id);
            if (trail.isPresent() && trail.get().getActive()) {
                TrailDto trailDto = convertToDto(trail.get());
                return ApiResponse.success(trailDto, "Trail retrieved successfully");
            } else {
                return ApiResponse.error("Trail not found");
            }
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve trail: " + e.getMessage());
        }
    }
    
    public ApiResponse<List<TrailDto>> getUserFavoriteTrails(Integer userId) {
        try {
            List<Trail> favoriteTrails = favoriteTrailRepository.findTrailsByUserId(userId);
            List<TrailDto> trailDtos = favoriteTrails.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            
            return ApiResponse.success(trailDtos, "Favorite trails retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve favorite trails: " + e.getMessage());
        }
    }
    
    public ApiResponse<String> addToFavorites(Integer userId, Integer trailId) {
        try {
            Optional<User> user = userRepository.findById(userId);
            Optional<Trail> trail = trailRepository.findById(trailId);
            
            if (!user.isPresent()) {
                return ApiResponse.error("User not found");
            }
            
            if (!trail.isPresent() || !trail.get().getActive()) {
                return ApiResponse.error("Trail not found");
            }
            
            if (favoriteTrailRepository.existsByUserIdAndTrailId(userId, trailId)) {
                return ApiResponse.error("Trail is already in favorites");
            }
            
            FavoriteTrail favoriteTrail = new FavoriteTrail(user.get(), trail.get());
            favoriteTrailRepository.save(favoriteTrail);
            
            return ApiResponse.success("Trail added to favorites successfully", "Trail added to favorites");
        } catch (Exception e) {
            return ApiResponse.error("Failed to add trail to favorites: " + e.getMessage());
        }
    }
    
    public ApiResponse<String> removeFromFavorites(Integer userId, Integer trailId) {
        try {
            Optional<FavoriteTrail> favoriteTrail = favoriteTrailRepository.findByUserIdAndTrailId(userId, trailId);
            
            if (!favoriteTrail.isPresent()) {
                return ApiResponse.error("Trail is not in favorites");
            }
            
            favoriteTrailRepository.delete(favoriteTrail.get());
            
            return ApiResponse.success("Trail removed from favorites successfully", "Trail removed from favorites");
        } catch (Exception e) {
            return ApiResponse.error("Failed to remove trail from favorites: " + e.getMessage());
        }
    }
    
    private TrailDto convertToDto(Trail trail) {
        TrailDto dto = new TrailDto();
        dto.setId(trail.getId());
        dto.setName(trail.getName());
        dto.setLength(trail.getLength());
        dto.setElevationGain(trail.getElevationGain());
        dto.setEstimatedTime(trail.getEstimatedTime());
        dto.setTrailType(trail.getTrailType());
        dto.setDifficulty(trail.getDifficulty());
        dto.setDescription(trail.getDescription());
        dto.setLatitude(trail.getLatitude());
        dto.setLongitude(trail.getLongitude());
        dto.setMainImage(trail.getMainImage());
        dto.setRating(trail.getRating());
        dto.setReviewCount(trail.getReviewCount());
        dto.setBestTime(trail.getBestTime());
        dto.setDogsAllowed(trail.getDogsAllowed());
        dto.setActive(trail.getActive());
        dto.setCreatedAt(trail.getCreatedAt());
        dto.setUpdatedAt(trail.getUpdatedAt());
        
        // Convert destination
        if (trail.getDestination() != null) {
            rs.hikeandbook.dto.DestinationDto destinationDto = new rs.hikeandbook.dto.DestinationDto();
            destinationDto.setId(trail.getDestination().getId());
            destinationDto.setName(trail.getDestination().getName());
            destinationDto.setCountry(trail.getDestination().getCountry());
            destinationDto.setRegion(trail.getDestination().getRegion());
            destinationDto.setCity(trail.getDestination().getCity());
            destinationDto.setDescription(trail.getDestination().getDescription());
            destinationDto.setImage(trail.getDestination().getImage());
            destinationDto.setLatitude(trail.getDestination().getLatitude());
            destinationDto.setLongitude(trail.getDestination().getLongitude());
            destinationDto.setCreatedAt(trail.getDestination().getCreatedAt());
            destinationDto.setUpdatedAt(trail.getDestination().getUpdatedAt());
            dto.setDestination(destinationDto);
        }
        
        // Convert activities
        if (trail.getActivities() != null) {
            List<rs.hikeandbook.dto.ActivityDto> activityDtos = trail.getActivities().stream()
                    .map(activity -> {
                        rs.hikeandbook.dto.ActivityDto activityDto = new rs.hikeandbook.dto.ActivityDto();
                        activityDto.setId(activity.getId());
                        activityDto.setName(activity.getName());
                        activityDto.setDescription(activity.getDescription());
                        activityDto.setIcon(activity.getIcon());
                        activityDto.setEmoji(activity.getEmoji());
                        activityDto.setCreatedAt(activity.getCreatedAt());
                        return activityDto;
                    })
                    .collect(Collectors.toList());
            dto.setActivities(activityDtos);
        }
        
        return dto;
    }
} 