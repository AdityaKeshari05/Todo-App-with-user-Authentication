package com.aditya.todoApp.Controller;

import com.aditya.todoApp.CorsConfig.JwtUtillity;
import com.aditya.todoApp.Model.AppUser;
import com.aditya.todoApp.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtillity jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser requestLogin){
        AppUser user = userRepo.findByUserName(requestLogin.getUserName())
                .orElseThrow(()->new RuntimeException("user not found"));

        if(!user.getUserPassword().equals(requestLogin.getUserPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(user.getUserName());
        return ResponseEntity.ok(Map.of("token",token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUser user){
        if(userRepo.findByUserName(user.getUserName()).isPresent()){
            return ResponseEntity.badRequest().body("Username Already Taken");
        }
        return ResponseEntity.ok(userRepo.save(user));
    }

}
