package com.user.lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="Photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "truck_provider_id",referencedColumnName = "id")
    private User truckProvider;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleList vehicle;

    private String path;

}
