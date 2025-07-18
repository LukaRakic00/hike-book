package rs.hikeandbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.hikeandbook.model.Trail;

import java.util.List;

@Repository
public interface TrailRepository extends JpaRepository<Trail, Integer> {
    
    @Query("SELECT t FROM Trail t WHERE t.active = true")
    Page<Trail> findAllActive(Pageable pageable);
    
    @Query("SELECT t FROM Trail t WHERE t.active = true AND t.difficulty = :difficulty")
    Page<Trail> findByDifficulty(@Param("difficulty") String difficulty, Pageable pageable);
    
    @Query("SELECT t FROM Trail t WHERE t.active = true AND t.destination.id = :destinationId")
    Page<Trail> findByDestinationId(@Param("destinationId") Integer destinationId, Pageable pageable);
    
    @Query("SELECT t FROM Trail t JOIN t.activities a WHERE t.active = true AND a.id = :activityId")
    Page<Trail> findByActivityId(@Param("activityId") Integer activityId, Pageable pageable);
    
    @Query("SELECT t FROM Trail t WHERE t.active = true AND t.difficulty = :difficulty AND t.destination.id = :destinationId")
    Page<Trail> findByDifficultyAndDestinationId(@Param("difficulty") String difficulty, 
                                                 @Param("destinationId") Integer destinationId, 
                                                 Pageable pageable);
    
    @Query("SELECT t FROM Trail t JOIN t.activities a WHERE t.active = true AND t.difficulty = :difficulty AND a.id = :activityId")
    Page<Trail> findByDifficultyAndActivityId(@Param("difficulty") String difficulty, 
                                             @Param("activityId") Integer activityId, 
                                             Pageable pageable);
    
    @Query("SELECT t FROM Trail t JOIN t.activities a WHERE t.active = true AND t.destination.id = :destinationId AND a.id = :activityId")
    Page<Trail> findByDestinationIdAndActivityId(@Param("destinationId") Integer destinationId, 
                                                 @Param("activityId") Integer activityId, 
                                                 Pageable pageable);
    
    @Query("SELECT t FROM Trail t JOIN t.activities a WHERE t.active = true AND t.difficulty = :difficulty AND t.destination.id = :destinationId AND a.id = :activityId")
    Page<Trail> findByDifficultyAndDestinationIdAndActivityId(@Param("difficulty") String difficulty, 
                                                              @Param("destinationId") Integer destinationId, 
                                                              @Param("activityId") Integer activityId, 
                                                              Pageable pageable);
    
    @Query("SELECT t FROM Trail t WHERE t.destination.id = :destinationId")
    List<Trail> findByDestinationId(@Param("destinationId") Integer destinationId);
    
    @Query("SELECT t FROM Trail t " +
           "LEFT JOIN t.activities a " +
           "WHERE t.destination.id = :destinationId " +
           "AND (:activityId IS NULL OR a.id = :activityId) " +
           "AND (:difficulty IS NULL OR t.difficulty = :difficulty)")
    List<Trail> findByDestinationIdAndFilters(
        @Param("destinationId") Integer destinationId,
        @Param("activityId") Integer activityId,
        @Param("difficulty") String difficulty
    );
} 