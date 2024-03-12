package com.user.lms.repository;

import com.user.lms.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query(value = "SELECT count(b.id) from booking as b", nativeQuery = true)
    long countBookings();

    @Modifying
    @Query(value = "UPDATE booking SET fuel_charge = :fuelCharge, toll_charge = :tollCharge, labourer_charge = :labourerCharge, total_amount = :totalAmount, is_tp_approved = :isTPApproved WHERE id = :bookingId", nativeQuery = true)
    void addCharges(@Param("bookingId") Long bookingId, int fuelCharge, int tollCharge,
                    int labourerCharge, int totalAmount, Boolean isTPApproved);

    @Modifying
    @Query(value = "UPDATE booking SET decline_reason = :reason, is_tp_approved = :isTPApproved WHERE id = :bookingId", nativeQuery = true)
    void declineReq(@Param("bookingId") Long bookingId, String reason, Boolean isTPApproved);

    @Query(value = "SELECT * FROM booking where isTPApproved=false", nativeQuery = true)
    Booking fetchAllDetails();

    @Query(value = "SELECT * FROM booking WHERE booking_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Booking> getBookingsByDate(String startDate, String endDate);

    @Query(value = "SELECT b from Booking b where  b.user.id = ?1")
    List<Booking> getBookingByUser(Long id);
}

