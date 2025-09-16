package rs.hikeandbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.hikeandbook.dto.ApiResponse;
import rs.hikeandbook.model.Destination;
import rs.hikeandbook.model.Trail;
import rs.hikeandbook.model.User;
import rs.hikeandbook.repository.DestinationRepository;
import rs.hikeandbook.repository.TrailRepository;
import rs.hikeandbook.repository.UserRepository;
import rs.hikeandbook.service.AdminService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TrailRepository trailRepository;
    
    @Autowired
    private DestinationRepository destinationRepository;
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        try {
            Map<String, Object> stats = adminService.getStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Failed to fetch stats");
            return ResponseEntity.status(500).body(error);
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Admin controller is working!");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> users = userRepository.findAllByOrderByCreatedAtDesc();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping("/trails")
    public ResponseEntity<List<Trail>> getTrails() {
        try {
            List<Trail> trails = trailRepository.findAllWithDestinationName();
            return ResponseEntity.ok(trails);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PostMapping("/trails")
    public ResponseEntity<Trail> createTrail(@RequestBody Trail trail) {
        try {
            Trail savedTrail = trailRepository.save(trail);
            return ResponseEntity.ok(savedTrail);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PutMapping("/trails/{id}")
    public ResponseEntity<Trail> updateTrail(@PathVariable Integer id, @RequestBody Trail trail) {
        try {
            if (!trailRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            trail.setId(id);
            Trail updatedTrail = trailRepository.save(trail);
            return ResponseEntity.ok(updatedTrail);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @DeleteMapping("/trails/{id}")
    public ResponseEntity<ApiResponse> deleteTrail(@PathVariable Integer id) {
        try {
            if (!trailRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            trailRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<String>(true, "Trail deleted successfully", "Trail deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<String>(false, null, "Failed to delete trail"));
        }
    }
    
    @GetMapping("/destinations")
    public ResponseEntity<List<Destination>> getDestinations() {
        try {
            List<Destination> destinations = destinationRepository.findAllByOrderByCreatedAtDesc();
            return ResponseEntity.ok(destinations);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PostMapping("/destinations")
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {
        try {
            Destination savedDestination = destinationRepository.save(destination);
            return ResponseEntity.ok(savedDestination);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PutMapping("/destinations/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Integer id, @RequestBody Destination destination) {
        try {
            if (!destinationRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            destination.setId(id);
            Destination updatedDestination = destinationRepository.save(destination);
            return ResponseEntity.ok(updatedDestination);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @DeleteMapping("/destinations/{id}")
    public ResponseEntity<ApiResponse> deleteDestination(@PathVariable Integer id) {
        try {
            if (!destinationRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            destinationRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<String>(true, "Destination deleted successfully", "Destination deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<String>(false, null, "Failed to delete destination"));
        }
    }
    
    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        try {
            String role = request.get("role");
            if (role == null || (!role.equals("user") && !role.equals("admin"))) {
                return ResponseEntity.badRequest().build();
            }
            
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            
            user.setRole(role);
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PutMapping("/users/{id}/status")
    public ResponseEntity<User> updateUserStatus(@PathVariable Integer id, @RequestBody Map<String, Boolean> request) {
        try {
            Boolean active = request.get("active");
            if (active == null) {
                return ResponseEntity.badRequest().build();
            }
            
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            
            user.setActive(active);
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
} 