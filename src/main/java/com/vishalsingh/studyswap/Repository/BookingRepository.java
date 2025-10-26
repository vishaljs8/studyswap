package com.vishalsingh.studyswap.Repository;

import com.vishalsingh.studyswap.Entity.BookingEntity;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface BookingRepository extends MongoRepository<BookingEntity,String> {
     List<BookingEntity> findByOwnerNameAndStatus(String ownerName, String status);


    List<BookingEntity> findByBorrowerName(String borrowerName);

}

