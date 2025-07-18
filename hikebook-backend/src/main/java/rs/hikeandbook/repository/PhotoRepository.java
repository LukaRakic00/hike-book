package rs.hikeandbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.hikeandbook.model.Photo;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    
    @Query("SELECT p FROM Photo p WHERE p.trail.id = :trailId")
    List<Photo> findByTrailId(@Param("trailId") Integer trailId);
    
    @Query("SELECT p FROM Photo p WHERE p.trail.id = :trailId")
    Page<Photo> findByTrailId(@Param("trailId") Integer trailId, Pageable pageable);
    
    @Query("SELECT p FROM Photo p WHERE p.user.id = :userId")
    List<Photo> findByUserId(@Param("userId") Integer userId);
    
    @Query("SELECT p FROM Photo p WHERE p.user.id = :userId")
    Page<Photo> findByUserId(@Param("userId") Integer userId, Pageable pageable);
    
    @Query("SELECT p FROM Photo p WHERE p.trail.id = :trailId AND p.isMain = true")
    Optional<Photo> findMainPhotoByTrailId(@Param("trailId") Integer trailId);
    
    @Query("SELECT p FROM Photo p WHERE p.trail.id = :trailId ORDER BY p.isMain DESC, p.createdAt DESC")
    List<Photo> findByTrailIdOrderByMainAndDate(@Param("trailId") Integer trailId);
    
    @Query("SELECT COUNT(p) FROM Photo p WHERE p.trail.id = :trailId")
    Long countByTrailId(@Param("trailId") Integer trailId);
} 