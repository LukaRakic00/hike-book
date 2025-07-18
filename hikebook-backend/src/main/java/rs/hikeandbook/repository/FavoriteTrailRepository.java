package rs.hikeandbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.hikeandbook.model.FavoriteTrail;
import rs.hikeandbook.model.Trail;
import rs.hikeandbook.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteTrailRepository extends JpaRepository<FavoriteTrail, Integer> {
    
    @Query("SELECT ft FROM FavoriteTrail ft WHERE ft.user.id = :userId")
    List<FavoriteTrail> findByUserId(@Param("userId") Integer userId);
    
    @Query("SELECT ft.trail FROM FavoriteTrail ft WHERE ft.user.id = :userId")
    List<Trail> findTrailsByUserId(@Param("userId") Integer userId);
    
    @Query("SELECT ft FROM FavoriteTrail ft WHERE ft.user.id = :userId AND ft.trail.id = :trailId")
    Optional<FavoriteTrail> findByUserIdAndTrailId(@Param("userId") Integer userId, @Param("trailId") Integer trailId);
    
    @Query("SELECT COUNT(ft) > 0 FROM FavoriteTrail ft WHERE ft.user.id = :userId AND ft.trail.id = :trailId")
    boolean existsByUserIdAndTrailId(@Param("userId") Integer userId, @Param("trailId") Integer trailId);
} 