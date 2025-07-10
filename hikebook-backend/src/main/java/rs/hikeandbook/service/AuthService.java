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
        // Proveri da li korisnik već postoji
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.error("Korisnik sa ovim email-om već postoji");
        }
        
        // Kreiraj novog korisnika
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        
        userRepository.save(user);
        
        return AuthResponse.success(null, "Korisnik uspešno registrovan");
    }
    
    public AuthResponse signin(SigninRequest request) {
        // Pronađi korisnika po email-u
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        
        if (user == null) {
            return AuthResponse.error("Korisnik sa ovim email-om ne postoji");
        }
        
        // Proveri lozinku
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return AuthResponse.error("Pogrešna lozinka");
        }
        
        // Ovde bi trebalo da generišete JWT token
        // Za sada vraćamo uspešan odgovor bez tokena
        return AuthResponse.success("dummy-token", "Uspešno prijavljivanje");
    }
} 