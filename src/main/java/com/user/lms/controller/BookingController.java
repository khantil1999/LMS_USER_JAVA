package com.user.lms.controller;

import com.user.lms.domain.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;
}
