package com.example.BikeApplication.controller;

import com.example.BikeApplication.domain.Booking;
import com.example.BikeApplication.domain.ServiceProvider;
import com.example.BikeApplication.service.BookingService;
import com.example.BikeApplication.service.ServiceProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ServiceProviderService serviceProviderService;
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    @PostMapping()
    public ResponseEntity<Booking> bookAppointment(@RequestBody Booking bookingRequest) {

        try {
            Booking booking = bookingService.bookAppointment(bookingRequest);
            return ResponseEntity.ok(booking);
        } catch (Exception ex) {
            logger.error("Error while booking appointment: {}", ex.toString());
            //System.out.println(ex.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceProvider> getServiceProviderById(@PathVariable String id) {
        ServiceProvider serviceProvider = serviceProviderService.findServiceProviderById(id);
        HttpStatus status = serviceProvider != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(serviceProvider);
    }


}
