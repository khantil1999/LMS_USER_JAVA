package com.user.lms.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class CancelBookingModel {
    private Boolean isChargesApplied;
    private int refundAmount;
    private int cancellationFee;
    private int cancellationPer;
    private int advanceAmount;

}
