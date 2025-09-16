package rs.hikeandbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.hikeandbook.repository.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TrailRepository trailRepository;
    
    @Autowired
    private DestinationRepository destinationRepository;
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private PhotoRepository photoRepository;
    
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            stats.put("users", userRepository.count());
            stats.put("trails", trailRepository.count());
            stats.put("destinations", destinationRepository.count());
            stats.put("activities", activityRepository.count());
            stats.put("photos", photoRepository.count());
            
            // Dodaj dodatne statistike ako postoje druge tabele
            // stats.put("reservations", reservationRepository.count());
            // stats.put("reviews", reviewRepository.count());
            
        } catch (Exception e) {
            // Ako neka tabela ne postoji, postavi 0
            stats.put("users", 0L);
            stats.put("trails", 0L);
            stats.put("destinations", 0L);
            stats.put("activities", 0L);
            stats.put("photos", 0L);
        }
        
        return stats;
    }
} 