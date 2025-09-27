package com.vishalsingh.studyswap.Controller;

import com.vishalsingh.studyswap.Entity.BookingEntity;
import com.vishalsingh.studyswap.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/borrow/{productId}/{borrowerName}")
    public BookingEntity borrowProduct(@PathVariable String productId,
                                       @PathVariable String borrowerName) {
        return bookingService.borrowProduct(productId, borrowerName);
    }

    @GetMapping("/my-bookings/{username}")
    public List<BookingEntity> getBookings(@PathVariable String username) {
        return bookingService.getBookings(username);
    }

    @PostMapping("/return/{bookingId}")
    public BookingEntity returnProduct(@RequestParam String bookingId){
         return bookingService.returnProduct(bookingId);
    }

    @GetMapping("/myReq/{ownerName}")
    public List<BookingEntity> myReqs(@PathVariable String ownerName){
        return bookingService.findByOwnerNameAndStatus(ownerName);

    }

    @PostMapping("/accept/{bookingId}/{productId}")
    public void acceptReqs(@RequestParam String bookingID,@RequestParam String productId){
        bookingService.acceptReqs(bookingID,productId);

    }

    @PostMapping("/reject/{bookingId}")
    public BookingEntity rejectReqs(@RequestParam  String bookingID ){
        return bookingService.rejectReqs(bookingID);
    }
}

