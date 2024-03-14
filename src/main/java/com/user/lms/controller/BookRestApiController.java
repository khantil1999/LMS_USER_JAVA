package com.user.lms.controller;

import com.user.lms.domain.BookingService;
import com.user.lms.entity.Booking;
import com.user.lms.models.BookingModel;
import com.user.lms.models.BookingSaveModel;
import com.user.lms.models.CancelBookingModel;
import com.user.lms.models.ConfirmBookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class BookRestApiController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/saveBooking")
     String saveBooking(@RequestBody BookingSaveModel bookingSaveModel, Principal principal){
        this.bookingService.saveBooking(bookingSaveModel, principal);
        return "Done";
    }

    @GetMapping("/bookings")
    List<BookingModel> getBookingByCurrentUser(Principal principal){
       return this.bookingService.getBookingByCurrentUser(principal);
    }

    @GetMapping("/booking")
    BookingModel getBookingById(@RequestParam(name = "id") String id){
        return this.bookingService.getBookingById(id);
    }

    @PostMapping("/confirmBooking")
    String confirmBooking(@RequestBody ConfirmBookingModel confirmBookingModel ){
        return this.bookingService.confirmBooking(confirmBookingModel);
    }

    @PutMapping("/cancelBooking/{bookingId}")
    String cancelBooking(@PathVariable(name = "bookingId") String bookingId, @RequestBody CancelBookingModel cancelBookingModel){
        return this.bookingService.cancelBooking(bookingId,cancelBookingModel);
    }
}
