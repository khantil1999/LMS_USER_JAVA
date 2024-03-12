package com.user.lms.domain;

import com.user.lms.entity.Booking;
import com.user.lms.models.BookingModel;
import com.user.lms.models.UserDetailsModel;
import com.user.lms.models.VehicleListModel;
import com.user.lms.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrentBookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    public List<BookingModel> getAllBookings() {
        List<Booking> booking = this.bookingRepository.findAll();
        List<BookingModel> detailsModels = new ArrayList<>();
        booking.forEach(bookings -> {
            BookingModel bookingModel = this.mapVehicle(bookings);
            detailsModels.add(bookingModel);

        });
        return detailsModels;
    }

    private BookingModel mapVehicle(Booking booking) {
       return BookingModel.fromEntity(booking);

    }
}
