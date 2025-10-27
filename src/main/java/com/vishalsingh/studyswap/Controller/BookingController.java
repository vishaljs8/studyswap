package com.vishalsingh.studyswap.Controller;

import com.vishalsingh.studyswap.Entity.BookingEntity;
import com.vishalsingh.studyswap.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/borrow")
    public BookingEntity borrowProduct(@RequestParam String productId, Authentication authentication) {
        String borrowerName = authentication.getName();
        return bookingService.borrowProduct(productId, borrowerName);
    }

    @GetMapping("/my-bookings")
    public List<BookingEntity> getBookings(Authentication authentication) {
        String username = authentication.getName();
        return bookingService.getBookings(username);
    }

    @PostMapping("/return/{bookingId}")
    public BookingEntity returnProduct(@PathVariable String bookingId){
         return bookingService.returnProduct(bookingId);
    }

    @GetMapping("/myReq")
    public List<BookingEntity> myReqs(Authentication authentication){
        String ownerName = authentication.getName();
        return bookingService.findByOwnerNameAndStatus(ownerName);

    }

    @PostMapping("/accept/{bookingId}/{productId}")
    public BookingEntity acceptReqs(@PathVariable String bookingId,@PathVariable String productId){
        return bookingService.acceptReqs(bookingId,productId);

    }

    @PostMapping("/reject/{bookingId}")
    public BookingEntity rejectReqs(@PathVariable  String bookingId ){
        return bookingService.rejectReqs(bookingId);
    }
}

