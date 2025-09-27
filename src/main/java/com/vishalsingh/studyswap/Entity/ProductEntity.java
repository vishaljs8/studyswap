package com.vishalsingh.studyswap.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "product")
@NoArgsConstructor
public class ProductEntity {

   @Id
   private ObjectId id;
   @NonNull
   @Indexed(unique = true)
   private String productName;
   private String description;
   private String status;
   private String ownerName;
}
