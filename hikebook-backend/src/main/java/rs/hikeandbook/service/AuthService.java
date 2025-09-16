package rs.hikeandbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.hikeandbook.dto.AuthResponse;
import rs.hikeandbook.dto.SigninRequest;
import rs.hikeandbook.dto.SigninResponse;
import rs.hikeandbook.dto.SignupRequest;
import rs.hikeandbook.model.User;
import rs.hikeandbook.repository.UserRepository;
import rs.hikeandbook.util.JwtUtil;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public AuthResponse signup(SignupRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.error("Email is already registered.");
        }
        
        // Create new user with default 'user' role
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhoneNumber());
        user.setRole("user"); // Default role
        
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
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return AuthResponse.error("Incorrect password.");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        return AuthResponse.success(token, "Login successful.");
    }
    
    public SigninResponse signinWithUserData(SigninRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        
        if (user == null || !user.getActive()) {
            return SigninResponse.error("Invalid email or password");
        }
        
        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return SigninResponse.error("Invalid email or password");
        }
        
        // Generate JWT token with role
        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole());
        
        // Create user data response
        SigninResponse.UserData userData = new SigninResponse.UserData(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getRole(),
            user.getRegistrationDate(),
            user.getActive()
        );
        
        return SigninResponse.success(token, userData);
    }
    
    public AuthResponse signout(String token) {
        if (token == null || token.isEmpty()) {
            return AuthResponse.error("Token is required for signout.");
        }
        
        // Extract token from header
        String actualToken = jwtUtil.extractTokenFromHeader(token);
        
        if (actualToken == null) {
            return AuthResponse.error("Invalid token format.");
        }
        
        // Validate token
        if (!jwtUtil.validateToken(actualToken)) {
            return AuthResponse.error("Invalid or expired token.");
        }
        
        // In a real implementation, you would add the token to a blacklist
        // For now, we'll just return a success response
        return AuthResponse.success(null, "Successfully signed out.");
    }
} 