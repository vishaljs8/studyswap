package com.vishalsingh.studyswap.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "booking")
public class BookingEntity{
    @Id
    private String id;
    private String productName;
    private String borrowerName;
    private String ownerName;
    private String productId;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;

}
