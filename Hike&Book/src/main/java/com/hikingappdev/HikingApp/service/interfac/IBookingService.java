package com.hikingappdev.HikingApp.service.interfac;


import com.hikingappdev.HikingApp.dto.Response;
import com.hikingappdev.HikingApp.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}
