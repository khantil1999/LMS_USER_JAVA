package com.user.lms.controller;

import com.user.lms.domain.BookingService;
import com.user.lms.models.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrentBookingRestApiController {

    @Autowired
    private BookingService bookingService;
    @GetMapping("/bookings")
    List<BookingModel> loadAllBookings() {

        return this.bookingService.getAllBookings();
    }

    @GetMapping("/booking")
    BookingModel getBookingById(@RequestParam("id") String id) {
        System.out.println("Get Booking id in controller---");
        System.out.println("id is--"+id);
        return this.bookingService.getBookingById(Long.parseLong(id));
    }

}
