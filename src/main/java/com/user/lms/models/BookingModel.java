package com.user.lms.models;


import com.user.lms.entity.User;
import com.user.lms.entity.VehicleList;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingModel {

    private String startDestination;

    private String endDestination;

    private int km;

    private Boolean isLabourer;

    private int noOfLabourers;

    private int fuelCharge;

    private int tollCharge;

    private int labourerCharge;

    private List<User> users;

    private List<VehicleList> vehicles;

}
