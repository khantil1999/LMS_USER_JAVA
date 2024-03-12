package com.user.lms.controller;

import com.user.lms.domain.CurrentBookingService;
import com.user.lms.models.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CurrentBookingController {

    @Autowired
    private CurrentBookingService currentBookingService;
    @GetMapping("/currentBooking")
    public String loadCurrentBookingPage(Model model, @RequestParam(name = "startDate",required = false)String startDate, @RequestParam(name = "endDate",required = false)String endDate)
    {
        System.out.println("ssss" + startDate + endDate);
        List<BookingModel> bookings = new ArrayList<>();
            bookings=currentBookingService.getAllBookings();
        model.addAttribute("bookings",bookings);
        return "currentBooking";
    }

    @GetMapping("/allDetails")
    public String showAllDetailsPage(){
        return "viewAlldetails";
    }

}
