package com.user.lms.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_destination")
    private String startDestination;

    @Column(name = "end_destination")
    private String endDestination;

    @Column(name = "km")
    private int km;

    @Column(name = "is_Labourer")
    private Boolean isLabourer;

    @Column(name = "no_of_labourers")
    private int noOfLabourers;

    @Column(name = "fuel_charge")
    private int fuelCharge;

    @Column(name = "toll_charge")
    private int tollCharge;

    @Column(name = "labourer_charge")
    private int labourerCharge;

    @OneToMany(mappedBy = "id",fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(mappedBy = "id",fetch = FetchType.EAGER)
    private List<VehicleList> vehicles;
}
