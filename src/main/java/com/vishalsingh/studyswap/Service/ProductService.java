package com.vishalsingh.studyswap.Service;

import com.vishalsingh.studyswap.Entity.ProductEntity;
import com.vishalsingh.studyswap.Entity.UserEntity;
import com.vishalsingh.studyswap.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;

    public  List<ProductEntity> getAll() {
        return productRepository.findAll();
    }


    public ProductEntity saveProduct(ProductEntity product , String username) {
        UserEntity user = userService.getUser(username);
        product.setOwnerName(username);
        product.setStatus("AVAILABLE");
        ProductEntity p =productRepository.save(product);
        user.getProductEntities().add(product);
        userService.updateUser(user);
       return p;
    }




    public void deleteProduct(String productName) {
        productRepository.deleteByProductName(productName);
    }

}
