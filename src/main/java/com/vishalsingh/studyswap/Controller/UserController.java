package com.vishalsingh.studyswap.Controller;

import com.vishalsingh.studyswap.Entity.ProductEntity;
import com.vishalsingh.studyswap.Entity.UserEntity;
import com.vishalsingh.studyswap.POJO.ProductPojo;
import com.vishalsingh.studyswap.POJO.UserPojo;
import com.vishalsingh.studyswap.Service.ProductService;
import com.vishalsingh.studyswap.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @PostMapping("/create-us")
    public void saveUser(@RequestBody UserPojo pojo){
        UserEntity user = new UserEntity();
        user.setPassword(pojo.getPassword());
        user.setUsername(pojo.getUsername());

         userService.saveUser(user);
    }

    @PostMapping("/create-prd")
    public ResponseEntity<ProductEntity> saveProduct(@RequestBody ProductPojo pojo, Authentication authentication ){
        String username = authentication.getName();
        ProductEntity product = new ProductEntity();
        product.setProductName(pojo.getProductName());
        product.setDescription(pojo.getDescription());
         ProductEntity p=productService.saveProduct(product,username);
         return ResponseEntity.ok(p);
    }


    @DeleteMapping("/delete-prd/{productName}")
    public void deleteProduct(@PathVariable String productName){
        productService.deleteProduct(productName);
    }


    @GetMapping("/own-product/{username}")
    public List<ProductEntity> getProduct(@PathVariable String username){
        return userService.getProductEntities(username);
    }
}
