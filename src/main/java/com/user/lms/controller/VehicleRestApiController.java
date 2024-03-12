package com.user.lms.controller;

import com.user.lms.domain.VehicleService;
import com.user.lms.models.VehicleDetailsModel;
import com.user.lms.repository.TalukaRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @GetMapping("/searchTP")
    List<VehicleDetailsModel> searchTp(@RequestParam(value = "startDestination", required = false) String startDestination,
                                 @RequestParam(value = "endDestination",required = false) String endDestination){
        if(startDestination != null && !startDestination.isEmpty() && endDestination != null && !endDestination.isEmpty()){
            return this.vehicleService.searchVehicles(startDestination,endDestination);
        }else{
          return   this.vehicleService.getAllVehicles();
        }

    }
}
