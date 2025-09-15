package com.project.Hotel.Management.Repository;

import com.project.Hotel.Management.model.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    Booking findByEmail(String email);

    boolean existsByBookingNumber(String string);

    Booking findByBookingNumber(String bookingNumber);
    @Modifying
    @Transactional
    //boolean deleteByBookingNumber(String bookingId);
    long deleteByBookingNumber(String bookingNumber);
}
