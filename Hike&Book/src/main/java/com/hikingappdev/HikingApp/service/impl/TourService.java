package com.hikingappdev.HikingApp.service.impl;

import com.hikingappdev.HikingApp.dto.Response;
import com.hikingappdev.HikingApp.dto.TourDTO;
import com.hikingappdev.HikingApp.entity.Tour;
import com.hikingappdev.HikingApp.exception.OurException;
import com.hikingappdev.HikingApp.repo.BookingRepository;
import com.hikingappdev.HikingApp.repo.TourRepository;
import com.hikingappdev.HikingApp.service.AwsS3Service;
import com.hikingappdev.HikingApp.service.interfac.ITourService;
import com.hikingappdev.HikingApp.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TourService implements ITourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AwsS3Service awsS3Service;

    @Override
    public Response addNewTour(MultipartFile photo, String tourType, BigDecimal tourPrice, String tourDescription, String destination, String difficultyLevel) {
        Response response = new Response();

        try {
            String imageUrl = awsS3Service.saveImageToS3(photo);
            Tour tour = new Tour();
            tour.setTourPhotoUrl(imageUrl);
            tour.setTourType(tourType);
            tour.setTourPrice(tourPrice);
            tour.setTourDescription(tourDescription);
            tour.setDestination(destination);
            tour.setDifficultyLevel(difficultyLevel);
            Tour savedTour = tourRepository.save(tour);
            TourDTO tourDTO = Utils.mapTourEntityToTourDTO(savedTour);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setTour(tourDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a tour: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllTourTypes() {
        return tourRepository.findDistinctTourTypes();
    }

    @Override
    public Response getAllTours() {
        Response response = new Response();

        try {
            List<Tour> tourList = tourRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<TourDTO> tourDTOList = Utils.mapTourListEntityToTourListDTO(tourList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setTourList(tourDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving tours: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteTour(Long tourId) {
        Response response = new Response();

        try {
            tourRepository.findById(tourId).orElseThrow(() -> new OurException("Tour Not Found"));
            tourRepository.deleteById(tourId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting a tour: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateTour(Long tourId, String tourDescription, String tourType, BigDecimal tourPrice, MultipartFile photo, String destination, String difficultyLevel) {
        Response response = new Response();

        try {
            String imageUrl = null;
            if (photo != null && !photo.isEmpty()) {
                imageUrl = awsS3Service.saveImageToS3(photo);
            }
            Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new OurException("Tour Not Found"));
            if (tourType != null) tour.setTourType(tourType);
            if (tourPrice != null) tour.setTourPrice(tourPrice);
            if (tourDescription != null) tour.setTourDescription(tourDescription);
            if (destination != null) tour.setDestination(destination);
            if (difficultyLevel != null) tour.setDifficultyLevel(difficultyLevel);
            if (imageUrl != null) tour.setTourPhotoUrl(imageUrl);

            Tour updatedTour = tourRepository.save(tour);
            TourDTO tourDTO = Utils.mapTourEntityToTourDTO(updatedTour);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setTour(tourDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating a tour: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getTourById(Long tourId) {
        Response response = new Response();

        try {
            Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new OurException("Tour Not Found"));
            TourDTO tourDTO = Utils.mapTourEntityToTourDTO(tour);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setTour(tourDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving a tour: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableToursByDateAndType(LocalDate startDate, LocalDate endDate, String tourType) {
        Response response = new Response();

        try {
            List<Tour> availableTours = tourRepository.findAvailableToursByDatesAndTypes(startDate, endDate, tourType);
            List<TourDTO> tourDTOList = Utils.mapTourListEntityToTourListDTO(availableTours);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setTourList(tourDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving available tours: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableTours() {
        Response response = new Response();

        try {
            List<Tour> tourList = tourRepository.getAllAvailableTours();
            List<TourDTO> tourDTOList = Utils.mapTourListEntityToTourListDTO(tourList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setTourList(tourDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving available tours: " + e.getMessage());
        }
        return response;
    }
}