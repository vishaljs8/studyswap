package com.vishalsingh.studyswap.Controller;

import com.vishalsingh.studyswap.Entity.UserEntity;
import com.vishalsingh.studyswap.Model.AuthRequest;
import com.vishalsingh.studyswap.Model.AuthResponse;
import com.vishalsingh.studyswap.POJO.UserPojo;
import com.vishalsingh.studyswap.Service.UserService;
import com.vishalsingh.studyswap.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/create-us")
    public UserEntity saveUser(@RequestBody UserPojo pojo){
        UserEntity user = new UserEntity();
        user.setPassword(pojo.getPassword());
        user.setUsername(pojo.getUsername());

        userService.saveUser(user);
        return user;
    }
}
