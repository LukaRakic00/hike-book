package rs.hikeandbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.hikeandbook.dto.ActivityDto;
import rs.hikeandbook.dto.ApiResponse;
import rs.hikeandbook.model.Activity;
import rs.hikeandbook.repository.ActivityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    
    @Autowired
    private ActivityRepository activityRepository;
    
    public ApiResponse<List<ActivityDto>> getAllActivities() {
        try {
            List<Activity> activities = activityRepository.findAll();
            List<ActivityDto> activityDtos = activities.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            
            return ApiResponse.success(activityDtos, "Activities retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve activities: " + e.getMessage());
        }
    }
    
    private ActivityDto convertToDto(Activity activity) {
        ActivityDto dto = new ActivityDto();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setDescription(activity.getDescription());
        dto.setIcon(activity.getIcon());
        dto.setEmoji(activity.getEmoji());
        dto.setCreatedAt(activity.getCreatedAt());
        return dto;
    }
} 