package rs.hikeandbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.hikeandbook.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
} 