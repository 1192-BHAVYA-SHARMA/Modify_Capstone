package com.example.BikeApplication.repository;

import com.example.BikeApplication.domain.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking>findByIdAndDateAndSlot(String id, String date, String slot);
    List<Booking> findByUserId(String userId);
}
