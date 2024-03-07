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

        BookingModel bookingModel = new BookingModel();
        System.out.println("Ss:"+booking);

        System.out.println("booking id for mapping the data---"+booking.getId());
        VehicleListModel vehicle= new VehicleListModel();
        vehicle.setId(booking.getVehicleList().getId());
        vehicle.setWheel(booking.getVehicleList().getWheel());
        vehicle.setManufacturer(booking.getVehicleList().getManufacturer());
        vehicle.setModel(booking.getVehicleList().getModel());
        vehicle.setCurrentMileage(booking.getVehicleList().getCurrentMileage());
        vehicle.setFuelType(booking.getVehicleList().getFuelType());
        vehicle.setCapacity(booking.getVehicleList().getCapacity());
        vehicle.setLicensePlate(booking.getVehicleList().getLicensePlate());

        bookingModel.setVehicles(vehicle);

        bookingModel.setId(booking.getId());

        UserDetailsModel userDetailsModel = new UserDetailsModel();
        userDetailsModel.setId(booking.getDriver().getId());
        userDetailsModel.setEmail(booking.getDriver().getEmail());
        userDetailsModel.setFirstName(booking.getDriver().getFirstName());
        userDetailsModel.setLastName(booking.getDriver().getLastName());
        userDetailsModel.setMobileNo(booking.getDriver().getMobileNo());

        bookingModel.setTruckProvider(userDetailsModel);

        UserDetailsModel userDetailsModel1 = new UserDetailsModel();
        userDetailsModel1.setId(booking.getUser().getId());
        userDetailsModel1.setEmail(booking.getUser().getEmail());
        userDetailsModel1.setFirstName(booking.getUser().getFirstName());
        userDetailsModel1.setLastName(booking.getUser().getLastName());
        userDetailsModel1.setMobileNo(booking.getUser().getMobileNo());

        bookingModel.setUser(userDetailsModel1);

        bookingModel.setKm(booking.getKm());
        bookingModel.setEndDestination(booking.getEndDestination());
        bookingModel.setFuelCharge(booking.getFuelCharge());
        bookingModel.setIsLabourer(booking.getIsLabourer());
        bookingModel.setLabourerCharge(booking.getLabourerCharge());
        bookingModel.setStartDestination(booking.getStartDestination());
        bookingModel.setTollCharge(booking.getTollCharge());
        bookingModel.setNoOfLabourers(booking.getNoOfLabourers());
        bookingModel.setTotalAmount(booking.getTotalAmount());
        bookingModel.setIsTPApproved(booking.getIsTPApproved());
        bookingModel.setBookingDate(booking.getBookingDate());

        return bookingModel;

    }
}
