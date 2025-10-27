package com.vishalsingh.studyswap.Service;


import com.vishalsingh.studyswap.Entity.BookingEntity;
import com.vishalsingh.studyswap.Entity.ProductEntity;
import com.vishalsingh.studyswap.Repository.BookingRepository;
import com.vishalsingh.studyswap.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ProductRepository productRepository;

    public BookingEntity borrowProduct(String productId, String borrowerName) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if ("BOOKED".equals(product.getStatus())) {
            throw new RuntimeException("Product already booked");
        }

        if (product.getOwnerName().equals(borrowerName)) {
            throw new RuntimeException("You cannot borrow your own product");
        }

        BookingEntity booking = new BookingEntity();
        booking.setProductId(productId);
        booking.setBorrowerName(borrowerName);
        booking.setOwnerName(product.getOwnerName());
        booking.setProductName(product.getProductName());
        booking.setType("BORROW");
        booking.setStatus("REQUESTED");
        booking.setStartDate(LocalDateTime.now());

        return bookingRepository.save(booking);
    }


    public List<BookingEntity> getBookings(String borrowerName) {
        return bookingRepository.findByBorrowerName(borrowerName);
    }


    public BookingEntity returnProduct(String bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
               .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus("RETURNED");
        booking.setEndDate(LocalDateTime.now());

        ProductEntity product = productRepository.findById(booking.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus("AVAILABLE");
        productRepository.save(product);

        return bookingRepository.save(booking);
    }

    public List<BookingEntity> findByOwnerNameAndStatus(String ownerName) {
        List<BookingEntity> booked = bookingRepository.findByOwnerNameAndStatus(ownerName,"REQUESTED");

           return booked;

    }

    public BookingEntity acceptReqs(String bookingId,String productId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new RuntimeException("Booking not found for accept"));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("product not found for accept"));

        booking.setStatus("ACTIVE");
        product.setStatus("BOOKED");
        bookingRepository.save(booking);
        productRepository.save(product);
        return booking;

    }


    public BookingEntity rejectReqs(String bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new RuntimeException("Booking not found for reject"));
        booking.setStatus("REJECTED");
        return bookingRepository.save(booking);
    }
}

