package rs.hikeandbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.hikeandbook.dto.AuthResponse;
import rs.hikeandbook.dto.SigninRequest;
import rs.hikeandbook.dto.SignupRequest;
import rs.hikeandbook.model.User;
import rs.hikeandbook.repository.UserRepository;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public AuthResponse signup(SignupRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.error("Email is already registered.");
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        
        userRepository.save(user);
        
        return AuthResponse.success(null, "User registered successfully.");
    }
    
    public AuthResponse signin(SigninRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        
        if (user == null) {
            return AuthResponse.error("User with this email does not exist.");
        }
        
        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return AuthResponse.error("Incorrect password.");
        }
        
        // Here you should generate JWT token
        // For now, return a successful response with a dummy token
        return AuthResponse.success("dummy-token", "Login successful.");
    }
    
    public AuthResponse signout(String token) {
        if (token == null || token.isEmpty()) {
            return AuthResponse.error("Token is required for signout.");
        }
        
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        // Here you should invalidate the JWT token
        // For now, we'll just return a success response
        // In a real implementation, you would:
        // 1. Validate the token
        // 2. Add it to a blacklist
        // 3. Or use a shorter expiration time
        
        return AuthResponse.success(null, "Successfully signed out.");
    }
} 