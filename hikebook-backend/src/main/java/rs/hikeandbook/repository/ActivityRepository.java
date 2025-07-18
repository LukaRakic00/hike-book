package rs.hikeandbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.hikeandbook.model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
} 