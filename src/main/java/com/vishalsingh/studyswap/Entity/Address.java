package com.vishalsingh.studyswap.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
public class Address {

    private String city;
    @Id
    private String id;
}
