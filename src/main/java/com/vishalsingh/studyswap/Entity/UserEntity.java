package com.vishalsingh.studyswap.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "student")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private String username;
    @NonNull
    private String password;
    private String email;
    @DBRef
    private List<ProductEntity> productEntities = new ArrayList<>();
    @DBRef
    private List<Address> address = new ArrayList<>();

}
