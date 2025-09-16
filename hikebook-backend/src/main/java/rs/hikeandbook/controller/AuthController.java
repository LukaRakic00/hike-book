package rs.hikeandbook.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.hikeandbook.dto.AuthResponse;
import rs.hikeandbook.dto.SigninRequest;
import rs.hikeandbook.dto.SigninResponse;
import rs.hikeandbook.dto.SignupRequest;
import rs.hikeandbook.service.AuthService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        AuthResponse response = authService.signup(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", response.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SigninRequest request) {
        SigninResponse response = authService.signinWithUserData(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", response.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/signout")
    public ResponseEntity<?> signout(@RequestHeader(value = "Authorization", required = false) String token) {
        AuthResponse response = authService.signout(token);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", response.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
} 