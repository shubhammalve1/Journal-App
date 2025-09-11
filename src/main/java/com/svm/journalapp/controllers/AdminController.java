package com.svm.journalapp.controllers;

import com.svm.journalapp.entities.User;
import com.svm.journalapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();

        if(users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdminUser(@RequestBody User user){
        userService.saveNewAdmin(user);
    }
}
