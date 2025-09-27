package com.vishalsingh.studyswap.Service;

import com.vishalsingh.studyswap.Entity.ProductEntity;
import com.vishalsingh.studyswap.Entity.UserEntity;
import com.vishalsingh.studyswap.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity getUser(String username){

        return userRepository.findByUsername(username);
    }

    public void saveUser(UserEntity user){
           user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void updateUser(UserEntity user){

        userRepository.save(user);
    }

    public List<ProductEntity> getProductEntities(String username) {
        UserEntity user= userRepository.findByUsername(username);
        return user.getProductEntities();
    }
}
