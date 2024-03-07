package com.user.lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Vehicle_Details")
public class VehicleList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String licensePlate;
    private int capacity;
    private String manufacturer;
    private String fuelType;
    private int currentMileage;
    private String wheel;

    @ManyToOne
    @JoinColumn(name = "truck_provider_id")
    private User driver;


}
