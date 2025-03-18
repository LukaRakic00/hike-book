package com.hikingappdev.HikingApp.utils;

import com.hikingappdev.HikingApp.dto.BookingDTO;
import com.hikingappdev.HikingApp.dto.TourDTO;
import com.hikingappdev.HikingApp.dto.UserDTO;
import com.hikingappdev.HikingApp.entity.Booking;
import com.hikingappdev.HikingApp.entity.Tour;
import com.hikingappdev.HikingApp.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    // Generisanje random koda za potvrdu rezervacije
    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    // Mapiranje User entiteta na UserDTO
    public static UserDTO mapUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    // Mapiranje Tour entiteta na TourDTO
    public static TourDTO mapTourEntityToTourDTO(Tour tour) {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setId(tour.getId());
        tourDTO.setTourType(tour.getTourType());
        tourDTO.setTourPrice(tour.getTourPrice());
        tourDTO.setTourPhotoUrl(tour.getTourPhotoUrl());
        tourDTO.setTourDescription(tour.getTourDescription());
        tourDTO.setDestination(tour.getDestination());
        tourDTO.setDifficultyLevel(tour.getDifficultyLevel());
        return tourDTO;
    }

    // Mapiranje Booking entiteta na BookingDTO
    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        return bookingDTO;
    }

    // Mapiranje Tour entiteta na TourDTO sa rezervacijama
    public static TourDTO mapTourEntityToTourDTOPlusBookings(Tour tour) {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setId(tour.getId());
        tourDTO.setTourType(tour.getTourType());
        tourDTO.setTourPrice(tour.getTourPrice());
        tourDTO.setTourPhotoUrl(tour.getTourPhotoUrl());
        tourDTO.setTourDescription(tour.getTourDescription());
        tourDTO.setDestination(tour.getDestination());
        tourDTO.setDifficultyLevel(tour.getDifficultyLevel());

        if (tour.getBookings() != null) {
            tourDTO.setBookings(tour.getBookings().stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
        }
        return tourDTO;
    }

    // Mapiranje Booking entiteta na BookingDTO sa dodatnim informacijama
    public static BookingDTO mapBookingEntityToBookingDTOPlusBookedTours(Booking booking, boolean mapUser) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        if (mapUser) {
            bookingDTO.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
        }

        if (booking.getTour() != null) {
            TourDTO tourDTO = new TourDTO();
            tourDTO.setId(booking.getTour().getId());
            tourDTO.setTourType(booking.getTour().getTourType());
            tourDTO.setTourPrice(booking.getTour().getTourPrice());
            tourDTO.setTourPhotoUrl(booking.getTour().getTourPhotoUrl());
            tourDTO.setTourDescription(booking.getTour().getTourDescription());
            tourDTO.setDestination(booking.getTour().getDestination());
            tourDTO.setDifficultyLevel(booking.getTour().getDifficultyLevel());
            bookingDTO.setTour(tourDTO);
        }
        return bookingDTO;
    }

    // Mapiranje User entiteta na UserDTO sa rezervacijama i turama
    public static UserDTO mapUserEntityToUserDTOPlusUserBookingsAndTours(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        if (!user.getBookings().isEmpty()) {
            userDTO.setBookings(user.getBookings().stream()
                    .map(booking -> mapBookingEntityToBookingDTOPlusBookedTours(booking, false))
                    .collect(Collectors.toList()));
        }
        return userDTO;
    }

    // Mapiranje liste User entiteta na listu UserDTO
    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    // Mapiranje liste Tour entiteta na listu TourDTO
    public static List<TourDTO> mapTourListEntityToTourListDTO(List<Tour> tourList) {
        return tourList.stream().map(Utils::mapTourEntityToTourDTO).collect(Collectors.toList());
    }

    // Mapiranje liste Booking entiteta na listu BookingDTO
    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList) {
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }
}