package com.svm.journalapp.controllers;

import com.svm.journalapp.cache.AppCache;
import com.svm.journalapp.entities.User;
import com.svm.journalapp.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("{clear-app-cache}")
    @Operation(summary = "Reset - Clear app cache")
    public void clearAppCache(){
        appCache.init();
    }

    @GetMapping("/all-users")
    @Operation(summary = "Get All users - Only Admin can access this API end point")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();

        if(users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    @Operation(summary = "Please Create an Admin")
    public void createAdminUser(@RequestBody User user){
        userService.saveNewAdmin(user);
    }
}
