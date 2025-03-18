package com.hikingappdev.HikingApp.controller;

import com.hikingappdev.HikingApp.service.interfac.ITourService;
import com.hikingappdev.HikingApp.dto.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tours")
public class TourController {

    @Autowired
    private ITourService tourService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewTour(
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "tourType", required = false) String tourType,
            @RequestParam(value = "tourPrice", required = false) BigDecimal tourPrice,
            @RequestParam(value = "tourDescription", required = false) String tourDescription,
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "difficultyLevel", required = false) String difficultyLevel
    ) {
        if (photo == null || photo.isEmpty() || tourType == null || tourPrice == null || tourType.isBlank() || destination == null || difficultyLevel == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields (photo, tourType, tourPrice, destination, difficultyLevel)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = tourService.addNewTour(photo, tourType, tourPrice, tourDescription, destination, difficultyLevel);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllTours() {
        Response response = tourService.getAllTours();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getTourTypes() {
        return tourService.getAllTourTypes();
    }

    @GetMapping("/tour-by-id/{tourId}")
    public ResponseEntity<Response> getTourById(@PathVariable Long tourId) {
        Response response = tourService.getTourById(tourId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all-available-tours")
    public ResponseEntity<Response> getAvailableTours() {
        Response response = tourService.getAllAvailableTours();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/available-tours-by-date-and-type")
    public ResponseEntity<Response> getAvailableToursByDateAndType(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String tourType
    ) {
        if (startDate == null || tourType == null || tourType.isBlank() || endDate == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields (startDate, tourType, endDate)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = tourService.getAvailableToursByDateAndType(startDate, endDate, tourType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{tourId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateTour(@PathVariable Long tourId,
                                              @RequestParam(value = "photo", required = false) MultipartFile photo,
                                              @RequestParam(value = "tourType", required = false) String tourType,
                                              @RequestParam(value = "tourPrice", required = false) BigDecimal tourPrice,
                                              @RequestParam(value = "tourDescription", required = false) String tourDescription,
                                              @RequestParam(value = "destination", required = false) String destination,
                                              @RequestParam(value = "difficultyLevel", required = false) String difficultyLevel
    ) {
        Response response = tourService.updateTour(tourId, tourDescription, tourType, tourPrice, photo, destination, difficultyLevel);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{tourId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteTour(@PathVariable Long tourId) {
        Response response = tourService.deleteTour(tourId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}