package com.vishalsingh.studyswap.Controller;

import com.vishalsingh.studyswap.Entity.ProductEntity;
import com.vishalsingh.studyswap.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public List<ProductEntity> getEntity(){
        return productService.getEntity();
    }
}
