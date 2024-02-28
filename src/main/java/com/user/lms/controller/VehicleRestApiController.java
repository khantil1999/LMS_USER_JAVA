package com.user.lms.controller;

import com.user.lms.domain.VehicleService;
import com.user.lms.models.VehicleDetailsModel;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehicleRestApiController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    List<VehicleDetailsModel> loadAllVehicles() {
        return this.vehicleService.getAllVehicles();
    }

    @GetMapping("/vehicle")
    VehicleDetailsModel getVehicleById(@RequestParam("id") String id) {
        return this.vehicleService.getVehicleById(Long.parseLong(id));
    }
}
