package rs.hikeandbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.hikeandbook.model.Destination;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    
    @Query("SELECT d, COUNT(t) FROM Destination d LEFT JOIN Trail t ON d.id = t.destination.id " +
           "GROUP BY d.id, d.name, d.region, d.latitude, d.longitude, d.image " +
           "ORDER BY d.name")
    List<Object[]> findAllWithTrailCount();
    
    @Query("SELECT d FROM Destination d WHERE d.region = :region")
    List<Destination> findByRegion(@Param("region") String region);
} 