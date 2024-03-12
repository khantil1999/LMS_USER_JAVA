package com.user.lms.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaveModel {
    private Date bookingDate;
    private String startDestination;
    private String endDestination;
    private String goodsType;
    private int km;
    private long noOfLabourers;
    private long truckProviderId;
    private long vehicleId;

}
