package com.hikingappdev.HikingApp.service.interfac;

import com.hikingappdev.HikingApp.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ITourService {

    Response addNewTour(MultipartFile photo, String tourType, BigDecimal tourPrice, String tourDescription, String destination, String difficultyLevel);

    List<String> getAllTourTypes();

    Response getAllTours();

    Response deleteTour(Long tourId);

    Response updateTour(Long tourId, String tourDescription, String tourType, BigDecimal tourPrice, MultipartFile photo, String destination, String difficultyLevel);

    Response getTourById(Long tourId);

    Response getAvailableToursByDateAndType(LocalDate startDate, LocalDate endDate, String tourType);

    Response getAllAvailableTours();
}