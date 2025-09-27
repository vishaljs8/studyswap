package com.vishalsingh.studyswap.Repository;

import com.vishalsingh.studyswap.Entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface ProductRepository extends MongoRepository<ProductEntity,String> {


    void deleteByProductName(String productName);

}
