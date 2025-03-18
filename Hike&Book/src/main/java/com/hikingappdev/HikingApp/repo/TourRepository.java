package com.hikingappdev.HikingApp.repo;

import com.hikingappdev.HikingApp.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {

    @Query("SELECT DISTINCT t.tourType FROM Tour t")
    List<String> findDistinctTourTypes();

    @Query("SELECT t FROM Tour t WHERE t.tourType LIKE %:tourType% AND t.id NOT IN (SELECT bk.tour.id FROM Booking bk WHERE" +
            "(bk.checkInDate <= :endDate) AND (bk.checkOutDate >= :startDate))")
    List<Tour> findAvailableToursByDatesAndTypes(LocalDate startDate, LocalDate endDate, String tourType);

    @Query("SELECT t FROM Tour t WHERE t.id NOT IN (SELECT b.tour.id FROM Booking b)")
    List<Tour> getAllAvailableTours();
}