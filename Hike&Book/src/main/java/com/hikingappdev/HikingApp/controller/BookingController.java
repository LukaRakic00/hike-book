package com.hikingappdev.HikingApp.controller;

import com.hikingappdev.HikingApp.dto.Response;
import com.hikingappdev.HikingApp.entity.Booking;
import com.hikingappdev.HikingApp.service.interfac.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    // Rezervacija ture (za korisnike i admina)
    @PostMapping("/book-tour/{tourId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveBookings(@PathVariable Long tourId,
                                                 @PathVariable Long userId,
                                                 @RequestBody Booking bookingRequest) {
        Response response = bookingService.saveBooking(tourId, userId, bookingRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Dobijanje svih rezervacija (samo admin)
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings() {
        Response response = bookingService.getAllBookings();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Dobijanje rezervacije po kodu potvrde (za sve)
    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        Response response = bookingService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Otkazivanje rezervacije (za korisnike i admina)
    @DeleteMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId) {
        Response response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}