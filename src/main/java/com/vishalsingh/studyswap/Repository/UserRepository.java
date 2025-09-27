package com.vishalsingh.studyswap.Repository;

import com.vishalsingh.studyswap.Entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity,String> {
    UserEntity findByUsername(String username);


}

