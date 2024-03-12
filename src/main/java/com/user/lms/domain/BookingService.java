package com.user.lms.domain;

import com.user.lms.entity.Booking;
import com.user.lms.entity.User;
import com.user.lms.entity.VehicleList;
import com.user.lms.models.BookingModel;
import com.user.lms.models.BookingSaveModel;
import com.user.lms.models.ConfirmBookingModel;
import com.user.lms.repository.BookingRepository;
import com.user.lms.repository.UserRepository;
import com.user.lms.repository.VehicleListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleListRepository vehicleListRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public String saveBooking(BookingSaveModel bookingSaveModel, Principal principal) {
        User customer = this.userRepository.findExistingUser(principal.getName());
        VehicleList vehicle = this.vehicleListRepository.getReferenceById(bookingSaveModel.getVehicleId());
        if (customer != null && vehicle != null) {
            Booking booking = new Booking();
            booking.setBookingDate(bookingSaveModel.getBookingDate());
            booking.setStartDestination(bookingSaveModel.getStartDestination());
            booking.setEndDestination(bookingSaveModel.getEndDestination());
            booking.setKm(bookingSaveModel.getKm());
            booking.setGoodsType(bookingSaveModel.getGoodsType());
            booking.setDriver(vehicle.getDriver());
            booking.setVehicleList(vehicle);
            booking.setUser(customer);

            this.bookingRepository.save(booking);

        }
        return "done";
    }

    public List<BookingModel> getBookingByCurrentUser(Principal principal) {
        User customer = this.userRepository.findExistingUser(principal.getName());
        return this.bookingRepository.getBookingByUser(customer.getId())
                .stream().map(BookingModel::fromEntity).collect(Collectors.toList());
    }

    public BookingModel getBookingById(String id) {
        Booking booking = this.bookingRepository.getReferenceById(Long.parseLong(id));
        return BookingModel.fromEntity(booking);

    }

    public String confirmBooking(ConfirmBookingModel confirmBookingModel) {
        Booking booking = this.bookingRepository.getReferenceById((confirmBookingModel.getBookingId()));
        if (booking != null) {
            booking.setIsPartialPaymentReceived(true);
            booking.setBookingConfirmPaymentImagePath(confirmBookingModel.getPaymentConfirmImagePath());
            this.bookingRepository.save(booking);
        }
        return "Done";
    }
}
