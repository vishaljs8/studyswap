package com.vishalsingh.studyswap.Controller;

import com.vishalsingh.studyswap.Entity.UserEntity;
import com.vishalsingh.studyswap.Model.AuthRequest;
import com.vishalsingh.studyswap.Model.AuthResponse;
import com.vishalsingh.studyswap.POJO.UserPojo;
import com.vishalsingh.studyswap.Repository.UserRepository;
import com.vishalsingh.studyswap.Service.CustomUserDetailsService;
import com.vishalsingh.studyswap.Service.UserService;
import com.vishalsingh.studyswap.Util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUrl;

    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
        user.setEmail(pojo.getEmail());

        userService.saveUser(user);
        return user;
    }

    @GetMapping("/google/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("client_id", clientId);
            formData.add("client_secret", clientSecret);
            formData.add("code", code);
            formData.add("redirect_uri", redirectUrl);
            formData.add("grant_type", "authorization_code");


            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);



            ResponseEntity<Map> tokenResponse =
                    restTemplate.postForEntity("https://oauth2.googleapis.com/token", requestEntity, Map.class);

            System.out.println(tokenResponse.getBody());
            Map<String, Object> responseBody = tokenResponse.getBody();
            String idToken = (String) responseBody.get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);
            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = userInfoResponse.getBody();
                String email = (String) userInfo.get("email");
                UserDetails userDetails = null;
                try{
                    userDetails = userDetailsService.loadUserByUsername(email);
                }catch (Exception e){
                    UserEntity user = new UserEntity();
                    user.setUsername(email);
                    user.setEmail(email);
                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    userRepository.save(user);
                }
                String jwtToken = jwtUtil.generateToken(email);
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header("Location", "https://kart-wxpg.onrendor.com/Public?token=" + jwtToken)
                        .build();
//                return ResponseEntity.status(HttpStatus.FOUND)
//                        .header("Location", "http://localhost:5173/Public?token=" + jwtToken)
//                        .build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error("Exception occurred while handleGoogleCallback ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}

