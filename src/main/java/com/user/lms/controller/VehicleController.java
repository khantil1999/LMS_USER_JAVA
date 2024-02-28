package com.user.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VehicleController {

    @GetMapping("truck/details")
    public String loadTruckDetailsPage() {
        return "truckdetails";
    }
}
