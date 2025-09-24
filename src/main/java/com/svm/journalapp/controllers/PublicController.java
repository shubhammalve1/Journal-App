package com.svm.journalapp.controllers;

import com.svm.journalapp.dto.UserDTO;
import com.svm.journalapp.entities.User;
import com.svm.journalapp.services.UserDetailsServiceImplementation;
import com.svm.journalapp.services.UserService;
import com.svm.journalapp.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs")
public class PublicController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    @GetMapping("/health-check")
    @Operation(summary = "Please Check Health of Application")
    public String healthCheck(){
        return "Ok";
    }

    @PostMapping("/signup")
    @Operation(summary = "Please SignUp")
    public boolean signup(@RequestBody User user){
//        UserDTO newUser = new UserDTO();
//        newUser.setEmail(user.getEmail());
//        newUser.setUsername(user.getUsername());
//        newUser.setPassword(user.getPassword());
//        newUser.setSentimentAnalysis(user.isSentimentAnalysis());

        userService.saveNewUser(user);
        return true;
    }

    @PostMapping("/login")
    @Operation(summary = "Please Login")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword()));

            UserDetails userDetails = userDetailsServiceImplementation.loadUserByUsername(user.getUsername());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch(Exception e){
            log.error("Exception during Authentication Token creation ", e);
            return new ResponseEntity<>("Incorrect username or Password", HttpStatus.BAD_REQUEST);
        }
    }
}
